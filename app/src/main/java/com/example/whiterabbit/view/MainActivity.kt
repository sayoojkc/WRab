package com.example.whiterabbit.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whiterabbit.R
import com.example.whiterabbit.WRApplication
import com.example.whiterabbit.database.EmployeeEntity
import com.example.whiterabbit.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ItemClickListener {

    private lateinit var viewManager: LinearLayoutManager
    private lateinit var employeeList: ArrayList<EmployeeEntity>
    private lateinit var employeeAdapter: EmployeeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WRApplication.getInstance().initQue(this)

        val sharedPreferences = getSharedPreferences(
            getString(R.string.app_name), Context.MODE_PRIVATE
        )
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        if (sharedPreferences.getBoolean("shouldFetchFromServer", true)) {
            progressBar.visibility = View.VISIBLE
            viewModel.fetchEmployeesFromServer().observe(this, Observer<ArrayList<EmployeeEntity>> {
                viewModel.saveDataInDB(it)
                sharedPreferences.edit().putBoolean("shouldFetchFromServer", false).apply()
            })
        }

        employeeList = ArrayList()

        viewManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        employeeAdapter =
            EmployeeAdapter(employeeList, this)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = viewManager
        recyclerview.adapter = employeeAdapter
        val dec = DividerItemDecoration(this, (viewManager as LinearLayoutManager).orientation);
        recyclerview.addItemDecoration(dec)

        val searchText = viewModel.getSearchText()

        viewModel.getEmployeeList().observe(this, Observer<List<EmployeeEntity>> {
            if (it.isEmpty()) {
                return@Observer
            }
            progressBar.visibility = View.GONE
            employeeList = it.filter {
                it.name!!.contains(searchText, true) || it.email!!.contains(
                    searchText,
                    true
                )
            } as ArrayList<EmployeeEntity>
            employeeAdapter.updateData(employeeList)
            employeeAdapter.notifyDataSetChanged()
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.setSearchText(newText)

                employeeAdapter.updateData(employeeList.filter {
                    it.name!!.contains(newText, true) || it.email!!.contains(
                        newText, true
                    )
                } as ArrayList<EmployeeEntity>)
                employeeAdapter.notifyDataSetChanged()
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                // task HERE
                return false
            }

        })
    }

    override fun onClick(index: Int) {
        val clickedItem = employeeList[index]
        val parcel = EmployeeParcelable(
            clickedItem.name,
            clickedItem.email,
            clickedItem.phone,
            clickedItem.profileImage,
            clickedItem.username,
            clickedItem.website,
            clickedItem.company.bs,
            clickedItem.company.catchPhrase,
            clickedItem.company.companyName,
            clickedItem.address.city,
            clickedItem.address.street,
            clickedItem.address.suite,
            clickedItem.address.zipCode
        )
        val intent = Intent(this, DetailActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("parcel", parcel)
        intent.putExtra("bundle", bundle)
        startActivity(intent)
    }
}

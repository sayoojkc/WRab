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

        viewManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        employeeAdapter =
            EmployeeAdapter(ArrayList<EmployeeEntity>(), this)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = viewManager
        recyclerview.adapter = employeeAdapter
        val dec = DividerItemDecoration(this, (viewManager as LinearLayoutManager).orientation);
        recyclerview.addItemDecoration(dec)


        viewModel.employeeList.observe(this, Observer<List<EmployeeEntity>> {
            if (it.isEmpty()) {
                return@Observer
            }
            progressBar.visibility = View.GONE
            val employeeList = it as ArrayList<EmployeeEntity>
            employeeAdapter.updateData(employeeList)
            employeeAdapter.notifyDataSetChanged()
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                employeeAdapter.filter.filter(newText);
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                // task HERE
                return false
            }

        })
    }

    override fun onClick(clickedEmployee: EmployeeEntity) {
        val parcel = EmployeeParcelable(
            clickedEmployee.name,
            clickedEmployee.email,
            clickedEmployee.phone,
            clickedEmployee.profileImage,
            clickedEmployee.username,
            clickedEmployee.website,
            clickedEmployee.company.bs,
            clickedEmployee.company.catchPhrase,
            clickedEmployee.company.companyName,
            clickedEmployee.address.city,
            clickedEmployee.address.street,
            clickedEmployee.address.suite,
            clickedEmployee.address.zipCode
        )
        val intent = Intent(this, DetailActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("parcel", parcel)
        intent.putExtra("bundle", bundle)
        startActivity(intent)
    }
}

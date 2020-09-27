package com.example.whiterabbit.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whiterabbit.R
import com.example.whiterabbit.database.EmployeeEntity
import kotlinx.android.synthetic.main.row_emp.view.*

class EmployeeAdapter(
    private var employeeList: ArrayList<EmployeeEntity>,
    private var itemClickListener: ItemClickListener
) :
    RecyclerView.Adapter<EmployeeAdapter.MyViewHolder>(), Filterable {

    private var filteredList: List<EmployeeEntity>

    init {
        filteredList = ArrayList()
    }

    fun updateData(employeeList: ArrayList<EmployeeEntity>) {
        this.employeeList = employeeList
        this.filteredList = employeeList
    }

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_emp, parent, false)
        return MyViewHolder(textView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide
            .with(holder.itemView.context)
            .load(filteredList[position].profileImage)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.view.profileImage)
        holder.view.name.text = filteredList[position].name
        holder.view.companyName.text = filteredList[position].company.companyName
        holder.view.setOnClickListener {
            itemClickListener.onClick(filteredList[position])
        }
    }

    override fun getItemCount() = filteredList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                filteredList = if (charSearch.isEmpty()) {
                    employeeList
                } else {
                    val resultList = ArrayList<EmployeeEntity>()
                    for (entity in employeeList) {
                        if (entity.name!!.contains(charSearch, true)) {
                            resultList.add(entity)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as ArrayList<EmployeeEntity>
                notifyDataSetChanged()
            }

        }
    }
}
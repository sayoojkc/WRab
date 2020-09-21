package com.example.whiterabbit.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whiterabbit.R
import com.example.whiterabbit.database.EmployeeEntity
import kotlinx.android.synthetic.main.row_emp.view.*

class EmployeeAdapter(private var myDataset: ArrayList<EmployeeEntity>, private var itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<EmployeeAdapter.MyViewHolder>() {

    fun updateData(employeeList: ArrayList<EmployeeEntity>) {
        myDataset = employeeList;
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
            .load(myDataset[position].profileImage)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.view.profileImage)
        holder.view.name.text = myDataset[position].name
        holder.view.companyName.text = myDataset[position].company.companyName
        holder.view.setOnClickListener{
            itemClickListener.onClick(position)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}
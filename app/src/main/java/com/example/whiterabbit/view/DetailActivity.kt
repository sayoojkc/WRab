package com.example.whiterabbit.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.whiterabbit.R
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val bundle = intent.getBundleExtra("bundle")
        val employee = bundle!!.getParcelable<EmployeeParcelable>("parcel")

        Glide
            .with(this)
            .load(employee!!.profileImage)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(profileImage)
        name.text = employee.name
        email.text = employee.email
        phone.text = employee.phone
        userName.text = employee.username
        website.text = employee.website
        bs.text = employee.bs
        catchPhrase.text = employee.catchPhrase
        companyName.text = employee.companyName
        street.text = employee.street
        suite.text = employee.suite
        zipCode.text = employee.zipcode
        city.text = employee.city
    }
}

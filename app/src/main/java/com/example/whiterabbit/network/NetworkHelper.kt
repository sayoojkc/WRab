package com.example.whiterabbit.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.whiterabbit.WRApplication
import com.example.whiterabbit.database.Address
import com.example.whiterabbit.database.Company
import com.example.whiterabbit.database.EmployeeEntity
import org.json.JSONObject

class NetworkHelper {

    companion object {
        private const val url = "http://www.mocky.io/v2/5d565297300000680030a986";

        fun getData(): MutableLiveData<ArrayList<EmployeeEntity>> {
            Log.i("datais0 ", "calledmethod")

            val data: MutableLiveData<ArrayList<EmployeeEntity>> = MutableLiveData()
            val jsonArrayRequest = JsonArrayRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    Log.i("datais1 ", response.toString())
                    if (response != null) {
                        val empArray = ArrayList<EmployeeEntity>()
                        for (index in 0 until response.length()) {
                            val jsonObject: JSONObject = response.get(index) as JSONObject;
                            val id = jsonObject.getInt("id")
                            val name = jsonObject.getString("name")
                            val username = jsonObject.getString("username")
                            val profileImage = jsonObject.getString("profile_image")
                            val phone = jsonObject.getString("phone")
                            val website = jsonObject.getString("website")
                            val email = jsonObject.getString("email")
                            val company =
                                Company()
                            if (jsonObject.optJSONObject("company") != null) {
                                company.companyName =
                                    jsonObject.getJSONObject("company").getString("name")
                                company.catchPhrase =
                                    jsonObject.getJSONObject("company").getString("catchPhrase")
                                company.bs = jsonObject.getJSONObject("company").getString("bs")
                            }
                            val address =
                                Address()
                            address.city = jsonObject.getJSONObject("address").getString("city")
                            address.street =
                                jsonObject.getJSONObject("address").getString("street")
                            address.suite =
                                jsonObject.getJSONObject("address").getString("suite")
                            address.zipCode =
                                jsonObject.getJSONObject("address").getString("zipcode")
                            val obj = EmployeeEntity(
                                id,
                                name,
                                username,
                                email,
                                profileImage,
                                phone,
                                website,
                                company,
                                address
                            )
                            empArray.add(obj)

                            Log.i("datais3", empArray[index].name!!)

                        }
                        data.postValue(empArray)

                    }
                },
                Response.ErrorListener { error ->
                    // TODO: Handle error
                    Log.i("datais2 ", error.toString())

                }
            )

            jsonArrayRequest.retryPolicy = DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            WRApplication.getInstance().requestQueue.add(jsonArrayRequest)
            return data
        }
    }
}
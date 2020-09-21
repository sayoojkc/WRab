package com.example.whiterabbit.view

import android.os.Parcel
import android.os.Parcelable

class EmployeeParcelable(val name: String?,
                         val email: String?,
                         val phone: String?,
                         val profileImage: String?,
                         val username: String?,
                         val website: String?,
                         val bs: String?,
                         val catchPhrase: String?,
                         val companyName: String?,
                         val city: String?,
                         val street: String?,
                         val suite: String?,
                         val zipcode: String?
                         ): Parcelable {

    constructor(parcel: Parcel) : this(
        name = parcel.readString(),
        email = parcel.readString(),
        phone = parcel.readString(),
        profileImage = parcel.readString(),
        username = parcel.readString(),
        website = parcel.readString(),
        bs = parcel.readString(),
        catchPhrase = parcel.readString(),
        companyName = parcel.readString(),
        city = parcel.readString(),
        street = parcel.readString(),
        suite = parcel.readString(),
        zipcode = parcel.readString()
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(phone)
        parcel.writeString(profileImage)
        parcel.writeString(username)
        parcel.writeString(website)
        parcel.writeString(bs)
        parcel.writeString(catchPhrase)
        parcel.writeString(companyName)
        parcel.writeString(city)
        parcel.writeString(street)
        parcel.writeString(suite)
        parcel.writeString(zipcode)
    }


    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EmployeeParcelable> {
        override fun createFromParcel(parcel: Parcel): EmployeeParcelable {
            return EmployeeParcelable(parcel)
        }

        override fun newArray(size: Int): Array<EmployeeParcelable?> {
            return arrayOfNulls(size)
        }
    }

}
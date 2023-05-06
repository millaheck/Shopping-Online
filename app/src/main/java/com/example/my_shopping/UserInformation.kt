package com.example.my_shopping

import android.os.Parcel
import android.os.Parcelable

data class UserInformation(
    var id: Int,
    var email: String?,
    var username: String?,
    var password: String?,
    var name: FullName,
    var address: UserAddress,
    var phone: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable<FullName>(FullName::class.java.classLoader)!!,
        parcel.readParcelable<UserAddress>(UserAddress::class.java.classLoader)!!,
        parcel.readString()
    ){
    }
    override fun toString(): String {
        return "User(id=$id, email='$email', username='$username', password='$password', name=$name, address=$address, phone='$phone')"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(email)
        parcel.writeString(username)
        parcel.writeString(password)
        parcel.writeString(phone)
        parcel.writeParcelable(name, flags)
        parcel.writeParcelable(address, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserInformation> {
        override fun createFromParcel(parcel: Parcel): UserInformation {
            return UserInformation(parcel)
        }

        override fun newArray(size: Int): Array<UserInformation?> {
            return arrayOfNulls(size)
        }
    }
}

data class FullName(
    val firstname: String?,
    val lastname: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ){
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(firstname)
        parcel.writeString(lastname)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FullName> {
        override fun createFromParcel(parcel: Parcel): FullName {
            return FullName(parcel)
        }

        override fun newArray(size: Int): Array<FullName?> {
            return arrayOfNulls(size)
        }
    }
}

data class UserAddress(
    val city: String?,
    val street: String?,
    val number: Int,
    val zipcode: String?,
    val geolocation: UserGeolocation?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readParcelable(UserGeolocation::class.java.classLoader)
    ){
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(city)
        parcel.writeString(street)
        parcel.writeInt(number)
        parcel.writeString(zipcode)
        parcel.writeParcelable(geolocation, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserAddress> {
        override fun createFromParcel(parcel: Parcel): UserAddress {
            return UserAddress(parcel)
        }

        override fun newArray(size: Int): Array<UserAddress?> {
            return arrayOfNulls(size)
        }
    }
}

data class UserGeolocation(
    val lat: String?,
    val long: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(lat)
        parcel.writeString(long)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserGeolocation> {
        override fun createFromParcel(parcel: Parcel): UserGeolocation {
            return UserGeolocation(parcel)
        }

        override fun newArray(size: Int): Array<UserGeolocation?> {
            return arrayOfNulls(size)
        }
    }
}

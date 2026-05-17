package com.example.pokedog

import android.os.Parcel
import android.os.Parcelable

data class Dog(
    val id: Long? = null,
    val index: Int? = null,
    val name: String? = null,
    val type: String? = null,
    val heightFemale: String? = null,
    val heightMale: String? = null,
    val imageUrl: String? = null,
    val lifeExpectancy: String? = null,
    val temperament: String? = null,
    val weightFemale: String? = null,
    val weightMale: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeValue(index)
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeString(heightFemale)
        parcel.writeString(heightMale)
        parcel.writeString(imageUrl)
        parcel.writeString(lifeExpectancy)
        parcel.writeString(temperament)
        parcel.writeString(weightFemale)
        parcel.writeString(weightMale)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Dog> {
        override fun createFromParcel(parcel: Parcel) = Dog(parcel)
        override fun newArray(size: Int) = arrayOfNulls<Dog>(size)
    }
}
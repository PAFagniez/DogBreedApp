package com.paf.dogbreedapp.core.model

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class DogBreed(
    val breed: String,
    val subBreed: String? = null,
    var imageList: List<Uri>? = null): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(Uri.CREATOR)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(breed)
        parcel.writeString(subBreed)
        parcel.writeTypedList(imageList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DogBreed> {
        override fun createFromParcel(parcel: Parcel): DogBreed {
            return DogBreed(parcel)
        }

        override fun newArray(size: Int): Array<DogBreed?> {
            return arrayOfNulls(size)
        }
    }

}
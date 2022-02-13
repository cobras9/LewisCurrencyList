package com.devilsvirtue.cryptocom.ui.uio

import android.os.Parcel
import android.os.Parcelable

data class CurrencyUio(
    val id: String, val name: String, val symbol: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        id = parcel.readString().toString(),
        name = parcel.readString().toString(),
        symbol = parcel.readString().toString(),
    ) {
    }

     override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(symbol)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CurrencyUio> {
        override fun createFromParcel(parcel: Parcel): CurrencyUio {
            return CurrencyUio(parcel)
        }

        override fun newArray(size: Int): Array<CurrencyUio?> {
            return arrayOfNulls(size)
        }
    }
}

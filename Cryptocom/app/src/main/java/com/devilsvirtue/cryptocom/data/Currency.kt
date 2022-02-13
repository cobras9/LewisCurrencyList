package com.devilsvirtue.cryptocom.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devilsvirtue.cryptocom.ui.uio.CurrencyUio
import com.google.gson.annotations.SerializedName

@Entity(tableName = "currency")
data class Currency(
    @PrimaryKey()
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String,
)

fun Currency.mapDbToUio(): CurrencyUio {
    return CurrencyUio(
        id = this.id,
        name = this.name,
        symbol = this.symbol
    )
}
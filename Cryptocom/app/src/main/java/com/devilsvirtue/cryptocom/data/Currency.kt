package com.devilsvirtue.cryptocom.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devilsvirtue.cryptocom.domain.bo.CurrencyBo
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

// Not recommended complex app where models differentiate a lot
fun Currency.mapDbToUio(): CurrencyUio {
    return CurrencyUio(
        id = this.id,
        name = this.name,
        symbol = this.symbol
    )
}

fun Currency.mapDbToBo(): CurrencyBo {
    return CurrencyBo(
        id = this.id,
        name = this.name,
        symbol = this.symbol
    )
}

fun CurrencyBo.mapBoToUio(): CurrencyUio {
    return CurrencyUio(
        id = this.id,
        name = this.name,
        symbol = this.symbol
    )
}
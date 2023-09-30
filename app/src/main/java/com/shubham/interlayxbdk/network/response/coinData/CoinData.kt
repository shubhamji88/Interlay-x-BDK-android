package com.shubham.interlayxbdk.network.response.coinData


import com.google.gson.annotations.SerializedName

data class CoinData(
    @SerializedName("description")
    val description: Description?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("image")
    val image: Image?,
    @SerializedName("links")
    val links: Links?,
    @SerializedName("market_data")
    val marketData: MarketData?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("symbol")
    val symbol: String?
)
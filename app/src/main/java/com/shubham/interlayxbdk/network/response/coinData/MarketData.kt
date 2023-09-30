package com.shubham.interlayxbdk.network.response.coinData


import com.google.gson.annotations.SerializedName

data class MarketData(
    @SerializedName("current_price")
    val currentPrice: CurrentPrice?
)
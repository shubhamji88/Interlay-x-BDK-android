package com.shubham.interlayxbdk.network.response.coinData


import com.google.gson.annotations.SerializedName

data class CurrentPrice(
    @SerializedName("usd")
    val usd: Double?
)
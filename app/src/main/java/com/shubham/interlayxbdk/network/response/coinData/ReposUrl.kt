package com.shubham.interlayxbdk.network.response.coinData


import com.google.gson.annotations.SerializedName

data class ReposUrl(
    @SerializedName("bitbucket")
    val bitbucket: List<Any?>?,
    @SerializedName("github")
    val github: List<String?>?
)
package com.shubham.interlayxbdk.network


import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.shubham.interlayxbdk.network.response.coinData.CoinData
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

private const val BASE_URL="https://api.coingecko.com"
private val okHttpLogger=HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
private val okHttp= OkHttpClient.Builder().addInterceptor(okHttpLogger).build()

private val retrofit= Retrofit.Builder()
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(okHttp)
    .build()
interface ApiCall{
    @Headers("Content-Type: application/json")
    @GET("/api/v3/coins/{id}?localization=false&tickers=false&market_data=true&community_data=false&developer_data=false&sparkline=false")
    fun getCurrentDataForCoin(@Path("id") id:String): Deferred<CoinData>
}
object Api{
    val retrofitService: ApiCall =retrofit.create(ApiCall::class.java)

}
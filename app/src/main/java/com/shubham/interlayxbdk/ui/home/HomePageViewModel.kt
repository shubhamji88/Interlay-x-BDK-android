package com.shubham.interlayxbdk.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shubham.interlayxbdk.data.Repository
import kotlinx.coroutines.launch

class HomePageViewModel : ViewModel() {

    private val _btcValue = MutableLiveData<Double>()
    val btcValue: LiveData<Double>
        get() = _btcValue

    private val _ibtcValue = MutableLiveData<Double>()
    val ibtcValue: LiveData<Double>
        get() = _ibtcValue

    init {

        getCoinData("bitcoin")
        getCoinData("interbtc")
    }

    private fun getCoinData(coinId: String) {
        viewModelScope.launch {
            val response = Repository.getCoinMarketValue(coinId).await()
            val value = response.marketData?.currentPrice?.usd
            if (coinId == "bitcoin")
                _btcValue.postValue(value)
            if (coinId == "interbtc")
                _ibtcValue.postValue(value)
        }

    }
}
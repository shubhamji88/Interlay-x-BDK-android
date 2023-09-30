package com.shubham.interlayxbdk

import android.app.Application
import android.content.Context
import com.shubham.interlayxbdk.data.Repository
import com.shubham.interlayxbdk.data.Wallet
import com.shubham.interlayxbdk.utilities.SharedPreferencesManager

class InterlayApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // initialize Wallet object (singleton) with path variable
        Wallet.setPath(applicationContext.filesDir.toString())

        // initialize shared preferences manager object (singleton)
        val sharedPreferencesManager = SharedPreferencesManager(
            sharedPreferences = applicationContext.getSharedPreferences("current_wallet", MODE_PRIVATE)
        )

        // initialize Repository object with shared preferences
        Repository.setSharedPreferences(sharedPreferencesManager)
    }
}
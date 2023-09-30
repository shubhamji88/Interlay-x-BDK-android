package com.shubham.interlayxbdk.ui

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.shubham.interlayxbdk.R
import com.shubham.interlayxbdk.ui.recieve.RecieveFragment

class PopupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.popup_activity)
        var width= resources.displayMetrics.widthPixels*0.98
        var height= resources.displayMetrics.heightPixels*0.45
        supportFragmentManager
            .beginTransaction()
            .apply {
                if(intent.getStringExtra("address")?.isNotEmpty() == true){
                    add(R.id.popup_container,RecieveFragment())
                    window.attributes.gravity= Gravity.BOTTOM
                }
                commit()
            }
        window.setLayout(width.toInt(),height.toInt())
    }
}
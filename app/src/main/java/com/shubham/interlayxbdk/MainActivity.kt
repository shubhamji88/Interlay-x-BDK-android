package com.shubham.interlayxbdk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shubham.interlayxbdk.data.Repository
import com.shubham.interlayxbdk.databinding.ActivityMainBinding
import com.shubham.interlayxbdk.ui.home.HomePage
import com.shubham.interlayxbdk.ui.WalletFragment
const val TAG_HOME_FRAGMENT = "TAG_HOME_FRAGMENT"
const val TAG_WALLET_FRAGMENT = "TAG_WALLET_FRAGMENT"
class MainActivity : AppCompatActivity() {

    private val walletFragment = WalletFragment()
    private val homepageFragment = HomePage()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        supportFragmentManager.beginTransaction()
//            .add(R.id.fragmentContainer, walletFragment, TAG_WALLET_FRAGMENT)
//            .add(R.id.fragmentContainer, homepageFragment, TAG_HOME_FRAGMENT)
////            .commit()

        if(Repository.doesWalletExist()){
            updateFragment(TAG_HOME_FRAGMENT)
        }else{
            updateFragment(TAG_WALLET_FRAGMENT)
        }
    }

    fun updateFragment(fragment: String) {
        val transaction = supportFragmentManager.beginTransaction()
        when (fragment) {
            TAG_HOME_FRAGMENT -> transaction.replace(R.id.fragmentContainer, homepageFragment, TAG_HOME_FRAGMENT)
            TAG_WALLET_FRAGMENT -> transaction.replace(R.id.fragmentContainer, walletFragment, TAG_WALLET_FRAGMENT)
            else -> return // Handle the case for an unknown fragment tag here
        }
        transaction.commit()
    }

}
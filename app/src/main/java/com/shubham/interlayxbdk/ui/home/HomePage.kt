package com.shubham.interlayxbdk.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.shubham.interlayxbdk.R
import com.shubham.interlayxbdk.data.Repository
import com.shubham.interlayxbdk.data.Wallet
import com.shubham.interlayxbdk.databinding.HomePageBinding
import com.shubham.interlayxbdk.ui.PopupActivity
import com.shubham.interlayxbdk.utilities.formatInBtc

class HomePage:Fragment(R.layout.home_page) {

    private lateinit var binding: HomePageBinding
    private val viewModel by viewModels<HomePageViewModel> ()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = HomePageBinding.bind(view)

        if(Repository.doesWalletExist()){
            Wallet.createBlockchain()
            Wallet.loadExistingWallet()
        }
        updateDetails()

    }
    private fun updateDetails() {
        binding.btcAddressTextView.text = "Address: "+ Wallet.getLastUnusedAddress().address.asString()
        binding.btcBalanceTextView.text = "BTC: "+Wallet.getBalance().formatInBtc()
        viewModel.btcValue.observe(viewLifecycleOwner) {
            Log.d("Home",it.toString())
            binding.btcCurrentValueTextView.text = "Current 1 BTC value$ $it, Your Balance: "+(it*Wallet.getBalance().toLong())
        }
        binding.recieveBTCButton.setOnClickListener {
            val recieveIntent = Intent(context, PopupActivity::class.java)
            .putExtra("address" , Wallet.getLastUnusedAddress().address.asString() )
            startActivity(recieveIntent)
        }
        //iBtc apis yet to be implemented
        viewModel.ibtcValue.observe(viewLifecycleOwner) {
            Log.d("Home",it.toString())
            binding.btcCurrentValueTextView.text = "Current 1 iBTC value$ $it, Your Balance: 0"
        }
        binding.amountValue.doOnTextChanged { text, start, before, count ->
            if(text.toString().isNotEmpty()) {
                binding.receiveValue.text = (text.toString().toInt() * .09).toString() + " BTC"
                binding.bridgeFeeLabel.text = "Bridge Fee - "+ (text.toString().toInt() * .01).toString() + " BTC"
                binding.securityDepositLabel.text = "Security Deposit - - "+ (text.toString().toInt() * .001).toString() + " INTR"
                binding.txFeesLabel.text = "Tx fees - "+ (text.toString().toInt() * .002).toString() + " INTR"
            }
        }
    }
}
package com.shubham.interlayxbdk.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.shubham.interlayxbdk.MainActivity
import com.shubham.interlayxbdk.R
import com.shubham.interlayxbdk.TAG_HOME_FRAGMENT
import com.shubham.interlayxbdk.data.Wallet
import com.shubham.interlayxbdk.databinding.WalletCreationBinding

class WalletFragment :Fragment(R.layout.wallet_creation) {
    private lateinit var binding: WalletCreationBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = WalletCreationBinding.bind(view)
        updateDetails()

    }
    fun updateDetails(){
        binding.createWallet.setOnClickListener {
            Wallet.createWallet()
            (activity as? MainActivity)?.updateFragment(TAG_HOME_FRAGMENT)
        }
        binding.recoverWallet.setOnClickListener {
            binding.mnemoniceditText.visibility = View.VISIBLE
            binding.setupWallet.visibility = View.VISIBLE
        }

        binding.setupWallet.setOnClickListener {
            val mnemonic = binding.mnemoniceditText.text.toString()
            if(countWords(mnemonic)==12){
                Wallet.recoverWallet(mnemonic)
                (activity as? MainActivity)?.updateFragment(TAG_HOME_FRAGMENT)
            }else{
                Toast.makeText(context,"Please enter 12 word mnemonic",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun countWords(inputString: String): Int {
        val words = inputString.split("\\s+".toRegex())
        return words.size
    }

}
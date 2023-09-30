package com.shubham.interlayxbdk.ui.recieve

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.shubham.interlayxbdk.R
import com.shubham.interlayxbdk.data.Repository
import com.shubham.interlayxbdk.data.Wallet
import com.shubham.interlayxbdk.databinding.HomePageBinding
import com.shubham.interlayxbdk.databinding.PopupActivityBinding
import com.shubham.interlayxbdk.databinding.RecieveBtcBinding
import com.shubham.interlayxbdk.ui.home.HomePageViewModel

class RecieveFragment: Fragment(R.layout.recieve_btc) {
    private lateinit var binding: RecieveBtcBinding
    private val viewModel by viewModels<RecievePageViewModel> ()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = RecieveBtcBinding.bind(view)
        val address = activity?.intent?.getStringExtra("address") ?:""
        binding.addressTextView.text = address

        binding.qrImage.setImageBitmap(getQrCodeBitmap(address))

    }

    fun getQrCodeBitmap(qrCodeContent: String): Bitmap {
        val size = 512
        val bits = QRCodeWriter().encode(qrCodeContent, BarcodeFormat.QR_CODE, size, size)
        return Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
    }
}
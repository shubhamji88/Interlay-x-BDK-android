package com.shubham.interlayxbdk.data

import android.util.Log
import com.shubham.interlayxbdk.data.TAG
import org.bitcoindevkit.Blockchain
import org.bitcoindevkit.BlockchainConfig
import org.bitcoindevkit.ElectrumConfig

class ElectrumServer {
    private var useDefaultElectrum: Boolean = true
    private var default: Blockchain
    private var custom: Blockchain? = null
    private var customElectrumURL: String
    private val defaultElectrumURL = "ssl://electrum.blockstream.info:60002"

    init {
        val blockchainConfig = BlockchainConfig.Electrum(ElectrumConfig(
            url = defaultElectrumURL,
            socks5 = null,
            retry = 5u,
            timeout = null,
            stopGap = 10u,
            validateDomain = true
        ))
        customElectrumURL = ""
        default = Blockchain(blockchainConfig)
    }

    val server: Blockchain
        get() = if (useDefaultElectrum) this.default else this.custom!!

    fun createCustomElectrum(electrumURL: String) {
        customElectrumURL = electrumURL
        val blockchainConfig = BlockchainConfig.Electrum(ElectrumConfig(
            url = customElectrumURL,
            socks5 = null,
            retry = 5u,
            timeout = null,
            stopGap = 10u,
            validateDomain = true
        ))
        custom = Blockchain(blockchainConfig)
        useCustomElectrum()
        Log.i(TAG, "New Electrum Server URL : $customElectrumURL")
    }

    fun useCustomElectrum() {
        useDefaultElectrum = false
    }

    fun useDefaultElectrum() {
        useDefaultElectrum = true
    }

    fun isElectrumServerDefault(): Boolean {
        return useDefaultElectrum
    }

    fun getElectrumURL(): String {
        return if (useDefaultElectrum) {
            defaultElectrumURL
        } else {
            customElectrumURL
        }
    }
}

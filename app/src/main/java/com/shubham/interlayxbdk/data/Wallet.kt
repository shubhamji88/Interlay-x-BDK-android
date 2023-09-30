package com.shubham.interlayxbdk.data

import android.util.Log
import org.bitcoindevkit.*
import org.bitcoindevkit.Wallet as BdkWallet

const val TAG = "Wallet"

object Wallet {

    private lateinit var wallet: BdkWallet
    private lateinit var path: String
    private lateinit var electrumServer: ElectrumServer

    fun setPath(path: String) {
        Wallet.path = path
    }

    private fun initialize(
        descriptor: Descriptor,
        changeDescriptor: Descriptor,
    ) {
        val database = DatabaseConfig.Sqlite(SqliteDbConfiguration("$path/bdk-sqlite"))
        wallet = BdkWallet(
            descriptor,
            changeDescriptor,
            Network.TESTNET,
            database
        )
    }

    fun createBlockchain() {
        electrumServer = ElectrumServer()
        Log.i(TAG, "Current electrum URL : ${electrumServer.getElectrumURL()}")
    }


    fun createWallet() {
        val mnemonic: Mnemonic = Mnemonic(WordCount.WORDS12)
        val bip32ExtendedRootKey: DescriptorSecretKey  = DescriptorSecretKey(Network.TESTNET, mnemonic, null)
        val descriptor: Descriptor = Descriptor.newBip84(bip32ExtendedRootKey, KeychainKind.EXTERNAL, Network.TESTNET)
        val changeDescriptor: Descriptor = Descriptor.newBip84(bip32ExtendedRootKey, KeychainKind.INTERNAL, Network.TESTNET)
        initialize(
            descriptor = descriptor,
            changeDescriptor = changeDescriptor,
        )
        Repository.saveWallet(
            path,
            descriptor.asStringPrivate(),
            changeDescriptor.asStringPrivate()
        )
        Repository.saveMnemonic(mnemonic.asString())
    }


    fun loadExistingWallet() {
        val initialWalletData: RequiredInitialWalletData = Repository.getInitialWalletData()
        Log.i(TAG, "Loading existing wallet with descriptor: ${initialWalletData.descriptor}")
        Log.i(TAG, "Loading existing wallet with change descriptor: ${initialWalletData.changeDescriptor}")
        initialize(
            descriptor = Descriptor(initialWalletData.descriptor, Network.TESTNET),
            changeDescriptor = Descriptor(initialWalletData.changeDescriptor, Network.TESTNET),
        )
    }

    fun recoverWallet(recoveryPhrase: String) {
        val mnemonic = Mnemonic.fromString(recoveryPhrase)
        val bip32ExtendedRootKey = DescriptorSecretKey(Network.TESTNET, mnemonic, null)
        val descriptor: Descriptor = Descriptor.newBip84(bip32ExtendedRootKey, KeychainKind.EXTERNAL, Network.TESTNET)
        val changeDescriptor: Descriptor = Descriptor.newBip84(bip32ExtendedRootKey, KeychainKind.INTERNAL, Network.TESTNET)
        initialize(
            descriptor = descriptor,
            changeDescriptor = changeDescriptor,
        )
        Repository.saveWallet(
            path,
            descriptor.asStringPrivate(),
            changeDescriptor.asStringPrivate()
        )
        Repository.saveMnemonic(mnemonic.asString())
    }


    fun getBalance(): ULong = wallet.getBalance().total


    fun getLastUnusedAddress(): AddressInfo = wallet.getAddress(AddressIndex.LastUnused)

}
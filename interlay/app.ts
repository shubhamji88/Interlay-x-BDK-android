const { createInterBtcApi } = require("@interlay/interbtc-api");
const { createTestKeyring } = require("@polkadot/keyring/testing");
const { Keyring } = require('@polkadot/api');
const { Bitcoin, BitcoinAmount } = require("@interlay/monetary-js");
const { KeyringPair } = require('@polkadot/keyring/types');
const { BigSource } = require("big.js");
const express = require('express');

  
const app = express();
const PORT = 3000;
app.listen(PORT, (error) =>{
    if(!error)
        console.log("Server is Successfully Running, and App is listening on port "+ PORT)
    else 
        console.log("Error occurred, server can't start", error);
    }
);
// If you are using a local development environment
const PARACHAIN_ENDPOINT = "ws://127.0.0.1:9944";
// if you want to use the Interlay-hosted beta network
// const PARACHAIN_ENDPOINT = "wss://api.interlay.io/parachain";
const bitcoinNetwork = "regtest";
let interBTC = await createInterBtcApi(PARACHAIN_ENDPOINT, bitcoinNetwork);
const keyring = new Keyring({ type: 'sr25519' });


app.get('/getAmountForBtc', (req, res)=>{
    const amount = BitcoinAmount.from.BTC(0.001);
    res.send({
        amount
    })
}
)
app.post("/issuesRequest",async (req, res)=>{
    const privateKeyBytes = new TextEncoder().encode(req.body.privateKey);
    const keyPair: KeyringPair = keyring.addFromSeed(privateKeyBytes);
    interBTC.setAccount(keyPair);
    const amount = BitcoinAmount.from.BTC(0.001);
    const requestResults = await interBTC.issue.request(amount);
    res.send({
        requestResults
    })
})

app.post("/redeemRequest",async (req, res)=>{
    const privateKeyBytes = new TextEncoder().encode(req.body.privateKey);
    const keyPair: KeyringPair = keyring.addFromSeed(privateKeyBytes);
    interBTC.setAccount(keyPair);
    const amount = BitcoinAmount.from.BTC(0.001);
// request to issue interBTC
    const requestResults = await interBTC.redeem.request(amount, req.btcAddress);
    res.send({
        requestResults
    })
})
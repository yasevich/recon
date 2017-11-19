package com.github.yasevich.recon.input

import android.os.AsyncTask
import com.github.yasevich.recon.output.CurrencyRatesOutput

class WorkerThreadCurrencyRatesController(private val currencyRatesInput: CurrencyRatesInput) : CurrencyRatesInput {

    override var output: CurrencyRatesOutput?
        get() = currencyRatesInput.output
        set(value) {
            currencyRatesInput.output = value
        }

    override fun requestCurrencyRates(currencyCode: String?) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute {
            currencyRatesInput.requestCurrencyRates(currencyCode)
        }
    }

    override fun calculateRatesAmount(baseAmount: String) {
        currencyRatesInput.calculateRatesAmount(baseAmount)
    }
}

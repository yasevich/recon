package com.github.yasevich.recon.output

import android.os.Handler
import android.os.Looper
import com.github.yasevich.recon.model.CurrencyRateModel

class MainThreadCurrencyRatesOutput(private val currencyRatesOutput: CurrencyRatesOutput) : CurrencyRatesOutput {

    private val handler = Handler(Looper.getMainLooper())

    override fun onCurrencyRates(currencyRateModels: List<CurrencyRateModel>) {
        handler.post {
            currencyRatesOutput.onCurrencyRates(currencyRateModels)
        }
    }
}

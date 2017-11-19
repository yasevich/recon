package com.github.yasevich.recon.input

import com.github.yasevich.recon.output.CurrencyRatesOutput

interface CurrencyRatesInput : Input<CurrencyRatesOutput> {
    fun requestCurrencyRates(currencyCode: String? = null)
    fun calculateRatesAmount(baseAmount: String)
}

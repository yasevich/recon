package com.github.yasevich.recon.output

import com.github.yasevich.recon.model.CurrencyRateModel

interface CurrencyRatesOutput : Output {
    fun onCurrencyRates(currencyRateModels: List<CurrencyRateModel>)
}

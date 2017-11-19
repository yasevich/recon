package com.github.yasevich.recon.viewmodel

class CurrencyRatesViewModel(private val currencyRates: List<CurrencyRateViewModel> = emptyList()) : ViewModel {

    val size: Int = currencyRates.size

    operator fun get(index: Int): CurrencyRateViewModel = currencyRates[index]
}

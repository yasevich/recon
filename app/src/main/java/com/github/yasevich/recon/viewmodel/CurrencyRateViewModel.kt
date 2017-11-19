package com.github.yasevich.recon.viewmodel

class CurrencyRateViewModel(
        val currencyCode: CharSequence,
        val currencyName: CharSequence,
        val value: CharSequence,
        val isBase: Boolean)
    : ViewModel {

    val id: Long by lazy { currencyCode.hashCode().toLong() }
}

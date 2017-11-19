package com.github.yasevich.recon.input

import com.github.yasevich.recon.output.CurrencyRatesOutput
import java.util.Timer
import kotlin.concurrent.fixedRateTimer

class RecurrentCurrencyRatesController(private val currencyRatesInput: CurrencyRatesInput) : CurrencyRatesInput {

    private var timer: Timer? = null

    override var output: CurrencyRatesOutput?
        get() = currencyRatesInput.output
        set(value) {
            currencyRatesInput.output = value
            if (value == null) {
                timer?.cancel()
                timer = null
            }
        }

    override fun requestCurrencyRates(currencyCode: String?) {
        timer?.cancel()
        timer = fixedRateTimer(period = 1000L, action = {
            currencyRatesInput.requestCurrencyRates(currencyCode)
        })
    }

    override fun calculateRatesAmount(baseAmount: String) {
        currencyRatesInput.calculateRatesAmount(baseAmount)
    }
}

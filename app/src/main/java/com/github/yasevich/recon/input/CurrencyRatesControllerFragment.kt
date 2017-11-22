package com.github.yasevich.recon.input

import android.support.v4.app.Fragment
import com.github.yasevich.recon.output.CurrencyRatesOutput
import com.github.yasevich.recon.repository.NetworkCurrencyRateRepository

class CurrencyRatesControllerFragment : Fragment(), CurrencyRatesInput {

    private val controller = WorkerThreadCurrencyRatesController(
            RecurrentCurrencyRatesController(
                    CurrencyRatesController(
                            NetworkCurrencyRateRepository())))

    override var output: CurrencyRatesOutput?
        set(value) { controller.output = value }
        get() = controller.output

    init {
        retainInstance = true
    }

    override fun requestCurrencyRates(currencyCode: String?) {
        controller.requestCurrencyRates(currencyCode)
    }

    override fun calculateRatesAmount(baseAmount: String) {
        controller.calculateRatesAmount(baseAmount)
    }
}

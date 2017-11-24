package com.github.yasevich.recon.input

import android.support.v4.app.Fragment
import com.github.yasevich.recon.App
import com.github.yasevich.recon.output.CurrencyRatesOutput
import com.github.yasevich.recon.repository.NetworkCurrencyRateRepository
import com.github.yasevich.recon.repository.SharedPreferencesCurrencyRatesControllerStateRepository

class CurrencyRatesControllerFragment : Fragment(), CurrencyRatesInput {

    private val controller: CurrencyRatesInput = WorkerThreadCurrencyRatesController(
            RecurrentCurrencyRatesController(
                    CurrencyRatesController(NetworkCurrencyRateRepository(),
                            SharedPreferencesCurrencyRatesControllerStateRepository(App.instance))))

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

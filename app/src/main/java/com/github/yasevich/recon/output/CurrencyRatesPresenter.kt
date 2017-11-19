package com.github.yasevich.recon.output

import com.github.yasevich.recon.model.CurrencyRateModel
import com.github.yasevich.recon.view.CurrencyRatesView
import com.github.yasevich.recon.viewmodel.CurrencyRateViewModel
import com.github.yasevich.recon.viewmodel.CurrencyRatesViewModel
import java.math.BigDecimal

class CurrencyRatesPresenter(private val currencyRatesView: CurrencyRatesView) : CurrencyRatesOutput {

    override fun onCurrencyRates(currencyRateModels: List<CurrencyRateModel>) {
        currencyRatesView.show(CurrencyRatesViewModel(currencyRateModels.map {
            CurrencyRateViewModel(
                    it.currency.currencyCode,
                    it.currency.displayName,
                    it.amount.setScale(it.currency.defaultFractionDigits, BigDecimal.ROUND_HALF_EVEN).toPlainString(),
                    it.base == null)
        }))
    }
}

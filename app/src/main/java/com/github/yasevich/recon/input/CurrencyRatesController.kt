package com.github.yasevich.recon.input

import com.github.yasevich.recon.model.CurrencyRateModel
import com.github.yasevich.recon.output.CurrencyRatesOutput
import com.github.yasevich.recon.repository.CurrencyRateRepository
import java.math.BigDecimal

class CurrencyRatesController(private val currencyRateRepository: CurrencyRateRepository) : CurrencyRatesInput {

    override var output: CurrencyRatesOutput? = null

    private var baseAmount: BigDecimal = BigDecimal.ONE

    private var currencyRates: List<CurrencyRateModel> = emptyList()

    override fun requestCurrencyRates(currencyCode: String?) {
        if (currencyCode != null) {
            updateBaseAmount(currencyCode)
        }
        currencyRates = currencyRateRepository.selectAll(currencyCode)
        calculateRates()
    }

    override fun calculateRatesAmount(baseAmount: String) {
        this.baseAmount = if (baseAmount.isNotEmpty()) BigDecimal(baseAmount) else BigDecimal.ZERO
        calculateRates()
    }

    private fun updateBaseAmount(currencyCode: String) {
        baseAmount = currencyRates.find { it.currency.currencyCode == currencyCode }
                ?.let { calculateRate(it).amount } ?: BigDecimal.ONE
    }

    private fun calculateRates() {
        output?.onCurrencyRates(currencyRates.map(this::calculateRate).toList())
    }

    private fun calculateRate(currencyRateModel: CurrencyRateModel) =
            CurrencyRateModel(
                    currencyRateModel.currency,
                    currencyRateModel.amount.multiply(baseAmount),
                    currencyRateModel.base)
}

package com.github.yasevich.recon.input

import com.github.yasevich.recon.model.CurrencyRateModel
import com.github.yasevich.recon.model.CurrencyRatesControllerState
import com.github.yasevich.recon.output.CurrencyRatesOutput
import com.github.yasevich.recon.repository.CurrencyRateRepository
import com.github.yasevich.recon.repository.CurrencyRatesControllerStateRepository
import java.math.BigDecimal

class CurrencyRatesController(
        private val currencyRateRepository: CurrencyRateRepository,
        private val currencyRatesControllerStateRepository: CurrencyRatesControllerStateRepository
) : CurrencyRatesInput {

    override var output: CurrencyRatesOutput? = null

    private var currencyCode: String?
    private var baseAmount: BigDecimal

    private var currencyRates: List<CurrencyRateModel> = emptyList()

    init {
        val state = currencyRatesControllerStateRepository.getCurrencyRatesControllerState()
        currencyCode = state.currencyCode
        baseAmount = state.baseAmount ?: BigDecimal.ONE
    }

    override fun requestCurrencyRates(currencyCode: String?) {
        if (currencyCode != null) {
            this.currencyCode = currencyCode
            updateBaseAmount(currencyCode)
        }
        currencyRatesControllerStateRepository.saveCurrencyRatesControllerState(getState())
        currencyRates = currencyRateRepository.selectAll(this.currencyCode)
        calculateRates()
    }

    override fun calculateRatesAmount(baseAmount: String) {
        this.baseAmount = if (baseAmount.isNotEmpty()) BigDecimal(baseAmount) else BigDecimal.ZERO
        currencyRatesControllerStateRepository.saveCurrencyRatesControllerState(getState())
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

    private fun getState(): CurrencyRatesControllerState = CurrencyRatesControllerState(currencyCode, baseAmount)
}

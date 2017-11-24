package com.github.yasevich.recon.repository

import com.github.yasevich.recon.model.CurrencyRatesControllerState

interface CurrencyRatesControllerStateRepository : Repository {
    fun getCurrencyRatesControllerState(): CurrencyRatesControllerState
    fun saveCurrencyRatesControllerState(state: CurrencyRatesControllerState)
}

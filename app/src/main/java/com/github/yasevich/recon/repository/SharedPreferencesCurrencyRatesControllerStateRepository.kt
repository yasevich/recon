package com.github.yasevich.recon.repository

import android.content.Context
import com.github.yasevich.recon.model.CurrencyRatesControllerState
import java.math.BigDecimal

class SharedPreferencesCurrencyRatesControllerStateRepository(context: Context)
    : CurrencyRatesControllerStateRepository {

    private val preferences = context.getSharedPreferences(
            "SharedPreferencesCurrencyRatesControllerStateRepository", Context.MODE_PRIVATE)

    override fun getCurrencyRatesControllerState(): CurrencyRatesControllerState {
        return CurrencyRatesControllerState(
                preferences.getString(KEY_CURRENCY_CODE, null),
                preferences.getString(KEY_BASE_AMOUNT, null)?.let { BigDecimal(it) })
    }

    override fun saveCurrencyRatesControllerState(state: CurrencyRatesControllerState) {
        preferences.edit()
                .putString(KEY_CURRENCY_CODE, state.currencyCode)
                .putString(KEY_BASE_AMOUNT, state.baseAmount?.toPlainString())
                .apply()
    }

    companion object {
        private const val KEY_CURRENCY_CODE = "currencyCodee"
        private const val KEY_BASE_AMOUNT = "baseAmount"
    }
}
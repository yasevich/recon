package com.github.yasevich.recon.repository

import com.github.yasevich.recon.model.CurrencyRateModel

interface CurrencyRateRepository : Repository<CurrencyRateModel> {
    fun selectAll(currencyCode: String?): List<CurrencyRateModel>
}

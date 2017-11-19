package com.github.yasevich.recon.model

import java.math.BigDecimal
import java.util.Currency

class CurrencyRateModel(val currency: Currency, val amount: BigDecimal, val base: CurrencyRateModel? = null) : Model

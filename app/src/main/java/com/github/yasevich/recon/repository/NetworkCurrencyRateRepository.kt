package com.github.yasevich.recon.repository

import android.util.JsonReader
import com.github.yasevich.recon.model.CurrencyRateModel
import java.io.InputStreamReader
import java.math.BigDecimal
import java.net.URL
import java.util.Currency
import javax.net.ssl.HttpsURLConnection

class NetworkCurrencyRateRepository : CurrencyRateRepository {

    override fun selectAll(currencyCode: String?): List<CurrencyRateModel> {
        val base = if (currencyCode.isNullOrBlank()) "" else "?base=$currencyCode"
        val connection = URL("https://revolut.duckdns.org/latest$base").openConnection() as HttpsURLConnection
        try {
            return connection.inputStream.use {
                convert(readData(JsonReader(InputStreamReader(it))))
            }
        } finally {
            connection.disconnect()
        }
    }

    private fun convert(data: Data) = mutableListOf<CurrencyRateModel>().apply {
        val base = CurrencyRateModel(Currency.getInstance(data.base), BigDecimal.ONE)
        add(base)
        for (entry in data.rates) {
            add(CurrencyRateModel(Currency.getInstance(entry.key), entry.value, base))
        }
    }

    private fun readData(reader: JsonReader) = Data().apply {
        reader.beginObject()
        while (reader.hasNext()) {
            val name = reader.nextName()
            when (name) {
                "base" -> base = reader.nextString()
                "rates" -> readRates(reader, rates)
                else -> reader.skipValue()
            }
        }
    }

    private fun readRates(reader: JsonReader, rates: MutableMap<String, BigDecimal>) {
        reader.beginObject()
        while (reader.hasNext()) {
            rates[reader.nextName()] = BigDecimal.valueOf(reader.nextDouble())
        }
    }

    private class Data(
            var base: String? = null,
            val rates: MutableMap<String, BigDecimal> = mutableMapOf()
    )
}

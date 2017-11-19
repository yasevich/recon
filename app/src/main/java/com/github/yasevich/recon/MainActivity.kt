package com.github.yasevich.recon

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import com.github.yasevich.recon.input.CurrencyRatesController
import com.github.yasevich.recon.input.RecurrentCurrencyRatesController
import com.github.yasevich.recon.input.WorkerThreadCurrencyRatesController
import com.github.yasevich.recon.output.CurrencyRatesPresenter
import com.github.yasevich.recon.output.MainThreadCurrencyRatesOutput
import com.github.yasevich.recon.repository.NetworkCurrencyRateRepository
import com.github.yasevich.recon.view.CurrencyRatesAdapter
import com.github.yasevich.recon.view.CurrencyRatesView
import com.github.yasevich.recon.viewmodel.CurrencyRatesViewModel

class MainActivity : AppCompatActivity(), CurrencyRatesView, CurrencyRatesAdapter.EventListener {

    private val controller = WorkerThreadCurrencyRatesController(
            RecurrentCurrencyRatesController(
                    CurrencyRatesController(
                            NetworkCurrencyRateRepository())))

    private val adapter = CurrencyRatesAdapter(this)

    private val list: RecyclerView by lazy { findViewById<RecyclerView>(R.id.list) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list.adapter = adapter

        controller.output = MainThreadCurrencyRatesOutput(CurrencyRatesPresenter(this))
        controller.requestCurrencyRates()
    }

    override fun onDestroy() {
        super.onDestroy()
        controller.output = null
    }

    override fun show(viewModel: CurrencyRatesViewModel) {
        adapter.show(viewModel)
    }

    override fun onItemClick(currencyCode: String) {
        controller.requestCurrencyRates(currencyCode)
        list.scrollToPosition(0)
    }

    override fun onBaseValueChanged(value: String) {
        controller.calculateRatesAmount(value)
    }
}

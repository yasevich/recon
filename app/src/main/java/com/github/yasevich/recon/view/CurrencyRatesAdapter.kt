package com.github.yasevich.recon.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.yasevich.recon.viewmodel.CurrencyRatesViewModel

class CurrencyRatesAdapter(private val eventListener: EventListener) :
        RecyclerView.Adapter<CurrencyRateViewHolder>(), CurrencyRatesView {

    private var currencyRatesViewModel: CurrencyRatesViewModel = CurrencyRatesViewModel()

    init {
        setHasStableIds(true)
    }

    override fun onBindViewHolder(holder: CurrencyRateViewHolder, position: Int) {
        holder.apply {
            val model = currencyRatesViewModel[position]
            itemView.setOnClickListener { eventListener.onItemClick(model.currencyCode.toString()) }
            show(model)
        }.onValueChanged = eventListener::onBaseValueChanged
    }

    override fun getItemCount(): Int = currencyRatesViewModel.size

    override fun getItemId(position: Int): Long = currencyRatesViewModel[position].id

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyRateViewHolder =
            CurrencyRateViewHolder(LayoutInflater.from(parent.context), parent)

    override fun show(viewModel: CurrencyRatesViewModel) {
        val basesAreEqual = basesAreEqual(currencyRatesViewModel, viewModel)
        currencyRatesViewModel = viewModel
        if (basesAreEqual) {
            notifyItemRangeChanged(1, currencyRatesViewModel.size - 1)
        } else {
            notifyDataSetChanged()
        }
    }

    private fun basesAreEqual(model1: CurrencyRatesViewModel, model2: CurrencyRatesViewModel): Boolean =
            if (model1.size == 0 || model2.size == 0 || model1.size != model2.size) {
                false
            } else {
                model1[0].currencyCode == model2[0].currencyCode
            }

    interface EventListener {
        fun onItemClick(currencyCode: String)
        fun onBaseValueChanged(value: String)
    }
}

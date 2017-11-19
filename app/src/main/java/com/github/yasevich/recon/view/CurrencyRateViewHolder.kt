package com.github.yasevich.recon.view

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.github.yasevich.recon.R
import com.github.yasevich.recon.viewmodel.CurrencyRateViewModel
import com.github.yasevich.recon.widget.CustomEditText

class CurrencyRateViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.item_currency_rate, parent, false)), CurrencyRateView {

    var onValueChanged: ((String) -> Unit)? = null

    private val currencyCode: TextView = itemView.findViewById(R.id.currency_code)
    private val currencyName: TextView = itemView.findViewById(R.id.currency_name)
    private val value: CustomEditText = itemView.findViewById(R.id.value)

    override fun show(viewModel: CurrencyRateViewModel) {
        currencyCode.text = viewModel.currencyCode
        currencyName.text = viewModel.currencyName

        value.apply {
            clearTextChangeListeners()
            setText(viewModel.value)
            isEnabled = viewModel.isBase
            if (viewModel.isBase) {
                value.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable) {
                        onValueChanged?.invoke(s.toString())
                    }

                    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                        // does nothing
                    }

                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                        // does nothing
                    }
                })
            }
        }
    }
}

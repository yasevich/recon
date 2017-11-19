package com.github.yasevich.recon.widget

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText

class CustomEditText : EditText {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val watchers: MutableList<TextWatcher> = mutableListOf()

    override fun addTextChangedListener(watcher: TextWatcher) {
        watchers.add(watcher)
        super.addTextChangedListener(watcher)
    }

    override fun removeTextChangedListener(watcher: TextWatcher) {
        watchers.remove(watcher)
        super.removeTextChangedListener(watcher)
    }

    fun clearTextChangeListeners() {
        while (watchers.isNotEmpty()) {
            removeTextChangedListener(watchers.removeAt(0))
        }
    }
}

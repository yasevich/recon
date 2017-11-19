package com.github.yasevich.recon.view

import com.github.yasevich.recon.viewmodel.ViewModel

interface View<in T : ViewModel> {
    fun show(viewModel: T)
}

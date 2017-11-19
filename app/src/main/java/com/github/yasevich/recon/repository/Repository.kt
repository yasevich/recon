package com.github.yasevich.recon.repository

import com.github.yasevich.recon.model.Model

interface Repository<out T : Model> {
    fun selectAll() : List<T>
}

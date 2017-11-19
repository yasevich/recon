package com.github.yasevich.recon.input

import com.github.yasevich.recon.output.Output

interface Input<T : Output> {
    var output: T?
}

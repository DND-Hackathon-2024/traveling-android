package com.plass.travling.ui.component

import androidx.compose.ui.Modifier

fun Modifier.`if`(
    enabled: Boolean,
    modifier: Modifier.() -> Modifier
) = if (enabled) modifier() else this
package com.plass.travling.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

object TravelingColor {
    val Black = Color(0xFF000000)
    val White = Color(0xFFFFFFFF)
}

val LocalTravelingColor = compositionLocalOf { TravelingColor }
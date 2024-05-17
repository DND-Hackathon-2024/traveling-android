package com.plass.travling.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

object TravelingColor {
    val Black = Color(0xFF000000)
    val White = Color(0xFFFFFFFF)
    val Blue = Color(0xFF0078F9)
    val Gray = Color(0xFFCCCCD6)
    val Transparent = Color(0x00000000)
}

val LocalTravelingColor = compositionLocalOf { TravelingColor }
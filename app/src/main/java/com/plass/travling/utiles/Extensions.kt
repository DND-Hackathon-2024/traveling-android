package com.plass.travling.utiles

import android.content.Context
import android.widget.Toast

fun Context.showShortToast(
    text: String
) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}
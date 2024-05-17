package com.plass.travling.ui.component

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

fun Context.showShortToast(
    text: String
) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}
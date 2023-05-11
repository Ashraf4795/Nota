package com.nota.feature

import android.content.Context
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ColoredFloatingButton(private val context: Context, private val onButtonClick: (Int) -> Unit) {

    val buttons = listOf(
        FloatingActionButton(context).also {

        }
    )

}
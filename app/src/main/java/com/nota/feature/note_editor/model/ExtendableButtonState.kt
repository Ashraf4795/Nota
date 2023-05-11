package com.nota.feature.note_editor.model

import android.view.View

data class ExtendableButtonState(
    val shouldShow: Boolean
) {
    fun shouldShow(): Int {
        return if (shouldShow) View.VISIBLE else View.GONE
    }
}
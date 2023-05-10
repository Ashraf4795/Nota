package com.nota.feature.note_list.ui_model

import android.view.View

data class NoteListUiState(
    val isNoteListEmpty: Boolean = true,
) {
    fun shouldShowPlaceHolder(): Int{
        return if (isNoteListEmpty) {
            View.VISIBLE
        } else View.GONE
    }

    fun shouldShowNoteList(): Int{
        return if (isNoteListEmpty) {
            View.GONE
        } else View.VISIBLE
    }
}

package com.nota.feature.note_editor

import com.nota.data.local.room.entity.NoteEntity

sealed class DisplayNoteUiState
data class EditNoteState(val noteEntity: NoteEntity): DisplayNoteUiState()
object AddNewNoteState: DisplayNoteUiState()
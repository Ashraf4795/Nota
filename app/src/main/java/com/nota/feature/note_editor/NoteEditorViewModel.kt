package com.nota.feature.note_editor

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.nota.TAG
import com.nota.base.BaseViewModel
import com.nota.base.di.IoDispatcher
import com.nota.data.local.room.entity.NoteEntity
import com.nota.domain.contract.repository.NotaRepositoryContract
import com.nota.feature.common.Mode
import com.nota.feature.common.NoteAddingMode
import com.nota.feature.common.NoteEditingMode
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteEditorViewModel @Inject constructor(
    private val notaRepository: NotaRepositoryContract,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseViewModel() {
    private val _noteEntityState: MutableStateFlow<DisplayNoteUiState> =
        MutableStateFlow(AddNewNoteState)
    val noteEntityState: MutableStateFlow<DisplayNoteUiState> = _noteEntityState

    fun checkNoteEditingMode(mode: Mode) {
        viewModelScope.launch(dispatcher) {
            when (mode) {
                is NoteEditingMode -> {
                    try {
                        notaRepository.getNoteById(mode.noteId)
                            .catch {
                                Log.e(TAG, it.message.toString())
                            }
                            .collect { noteEntity ->
                                _noteEntityState.emit(EditNoteState(noteEntity))
                            }
                    } catch (ex: Exception) {
                        Log.e(TAG, ex.message.toString())
                    }
                }

                is NoteAddingMode -> {
                    _noteEntityState.emit(AddNewNoteState)
                }
            }
        }
    }

    fun save(scope: CoroutineScope, noteTitle: String, noteText: String) {
        scope.launch(dispatcher) {
            if (noteText.isNotBlank() || noteTitle.isNotBlank()) {
                val note = NoteEntity(noteTitle = noteTitle, noteText = noteText)
                notaRepository.insertNote(note)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun updateNote(scope: CoroutineScope, passedNoteEntity: NoteEntity) {
        scope.launch(dispatcher) {
            if (passedNoteEntity.noteText.isBlank() && passedNoteEntity.noteTitle.isBlank()) {
                notaRepository.deleteNote(passedNoteEntity)
                Log.d(TAG, "delete $passedNoteEntity")
            } else {
                notaRepository.updateNote(passedNoteEntity)
                Log.d(TAG, "update $passedNoteEntity")
            }
        }
    }
}
package com.nota.feature.note_editor

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.nota.TAG
import com.nota.base.BaseViewModel
import com.nota.base.di.ApplicationScope
import com.nota.base.di.IoDispatcher
import com.nota.data.local.room.ADD_NEW_NOTE
import com.nota.data.local.room.entity.NoteEntity
import com.nota.domain.contract.repository.NotaRepositoryContract
import com.nota.domain.state.Error
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteEditorViewModel @Inject constructor(
    private val notaRepository: NotaRepositoryContract,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseViewModel() {
    private val _noteEntityState: MutableStateFlow<DisplayNoteUiState> =
        MutableStateFlow(AddNewNoteState)
    val noteEntityState: MutableStateFlow<DisplayNoteUiState> = _noteEntityState
    private val note: Boolean = false

    fun checkNavArgs(noteDisplayArgs: NoteEditorFragmentArgs) {
        viewModelScope.launch(dispatcher) {
            val noteId = noteDisplayArgs.noteId
            val isNoteEditAction = noteId != ADD_NEW_NOTE

            if (isNoteEditAction) {
                try {
                    notaRepository.getNoteById(noteId)
                        .catch {
                            Log.e(TAG, it.message.toString())
                        }
                        .collect { noteEntity ->
                            _noteEntityState.emit(EditNoteState(noteEntity))
                        }
                } catch (ex: Exception) {
                    Log.e(TAG, ex.message.toString())
                }
            } else {
                _noteEntityState.emit(AddNewNoteState)
            }
        }
    }

    fun save(scope: CoroutineScope, noteTitle: String, noteText: String) {
        scope.launch(dispatcher) {
            if (noteText.isNotBlank() || noteTitle.isNotBlank()) {
                val note = NoteEntity(noteTitle = noteTitle, noteText = noteText)
                notaRepository.insertNote(note)
                Log.d(TAG, "insert $note")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "${this::class.java.name} is cleared")
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
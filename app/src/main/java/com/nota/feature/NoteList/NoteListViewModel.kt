package com.nota.feature.NoteList

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.nota.TAG
import com.nota.base.BaseViewModel
import com.nota.domain.contract.repository.NotaRepositoryContract
import com.nota.domain.state.Empty
import com.nota.domain.state.Error
import com.nota.domain.state.Loading
import com.nota.domain.state.State
import com.nota.domain.state.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteListViewModel(
    private val noteRepository: NotaRepositoryContract,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel() {

    private val _noteListState: MutableStateFlow<State> = MutableStateFlow(Loading)
    val noteListState: StateFlow<State> = _noteListState


    fun getAllNotes() {
        viewModelScope.launch(dispatcher) {
            try {
                noteRepository.getAllNotes().collect { notes ->
                    if (notes.isNotEmpty()) {
                        _noteListState.emit(Success(notes))
                    } else {
                        _noteListState.emit(Empty)
                    }
                }
            } catch (ex: Exception) {
                _noteListState.emit(Error(ex))
                Log.e(TAG, ex.message ?: "getAllNotes error")
            }
        }
    }

}
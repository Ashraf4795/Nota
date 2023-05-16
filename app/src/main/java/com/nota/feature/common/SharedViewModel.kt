package com.nota.feature.common

import android.util.Log
import androidx.lifecycle.ViewModel
import com.nota.TAG
import javax.inject.Inject

class SharedViewModel @Inject constructor() : ViewModel() {

    var mode: Mode = NoteAddingMode
        private set


    fun noteEditingMode(mode: Mode) {
        this.mode = mode
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "${this::class.java.name} is cleared")
    }
}

sealed class Mode
data class NoteEditingMode(val noteId: Long): Mode()
object NoteAddingMode: Mode()
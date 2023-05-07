package com.nota.data.local

import com.nota.domain.model.NoteEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSourceContract {

    fun getNotes(): Flow<List<NoteEntity>>
    suspend fun getNoteBy(noteId: Long): NoteEntity

}
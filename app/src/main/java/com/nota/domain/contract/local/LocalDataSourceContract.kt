package com.nota.data.local

import com.nota.data.local.room.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSourceContract {

    fun getAllNotes(): Flow<List<NoteEntity>>
    fun getNoteById(noteId: Long): Flow<NoteEntity>
    suspend fun insertNote(noteEntity: NoteEntity)
    suspend fun updateNote(newNote: NoteEntity)
    suspend fun deleteNote(note: NoteEntity)
}
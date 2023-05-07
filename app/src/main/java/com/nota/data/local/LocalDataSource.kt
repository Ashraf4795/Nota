package com.nota.data.local

import com.nota.data.local.room.NoteDAO
import com.nota.data.local.room.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource (private val noteDAO: NoteDAO) : LocalDataSourceContract {

    override fun getAllNotes(): Flow<List<NoteEntity>> {
        return noteDAO.getAllNotes()
    }

    override suspend fun getNoteById(noteId: Long): NoteEntity {
        return noteDAO.getNoteById(noteId)
    }

    override suspend fun insertNote(noteEntity: NoteEntity) {
        noteDAO.insertNote(noteEntity)
    }

    override suspend fun updateNote(newNote: NoteEntity) {
        noteDAO.updateNote(newNote)
    }

    override suspend fun deleteNote(note: NoteEntity) {
        noteDAO.deleteNote(note)
    }

}
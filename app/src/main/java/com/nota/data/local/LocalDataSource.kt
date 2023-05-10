package com.nota.data.local

import com.nota.data.local.room.NoteDAO
import com.nota.data.local.room.entity.NoteEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor (private val noteDAO: NoteDAO) : LocalDataSourceContract {

    override fun getAllNotes(): Flow<List<NoteEntity>> {
        return noteDAO.getAllNotes()
    }

    override fun getNoteById(noteId: Long): Flow<NoteEntity> {
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
package com.nota.data.repository

import com.nota.data.local.LocalDataSource
import com.nota.data.local.LocalDataSourceContract
import com.nota.data.local.room.entity.NoteEntity
import com.nota.data.remote.RemoteDataSource
import com.nota.domain.contract.remote.RemoteDataSourceContract
import com.nota.domain.contract.repository.NotaRepositoryContract
import kotlinx.coroutines.flow.Flow

class NotaRepository(
    private val localDataSource: LocalDataSourceContract,
    private val remoteDataSource: RemoteDataSourceContract
) : NotaRepositoryContract {

    override fun getAllNotes(): Flow<List<NoteEntity>> {
        return localDataSource.getAllNotes()
    }

    override suspend fun getNoteById(noteId: Long): NoteEntity {
       return localDataSource.getNoteById(noteId)
    }

    override suspend fun insertNote(noteEntity: NoteEntity) {
        localDataSource.insertNote(noteEntity)
    }

    override suspend fun updateNote(newNote: NoteEntity) {
        localDataSource.updateNote(newNote)
    }

    override suspend fun deleteNote(note: NoteEntity) {
        localDataSource.deleteNote(note)
    }
}
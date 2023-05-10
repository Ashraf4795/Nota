package com.nota.data.repository

import com.nota.base.di.IoDispatcher
import com.nota.data.local.LocalDataSource
import com.nota.data.local.LocalDataSourceContract
import com.nota.data.local.room.entity.NoteEntity
import com.nota.data.remote.RemoteDataSource
import com.nota.domain.contract.remote.RemoteDataSourceContract
import com.nota.domain.contract.repository.NotaRepositoryContract
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NotaRepository @Inject constructor(
    private val localDataSource: LocalDataSourceContract,
    private val remoteDataSource: RemoteDataSourceContract,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : NotaRepositoryContract {

    override fun getAllNotes(): Flow<List<NoteEntity>> {
        return localDataSource.getAllNotes()
    }

    override fun getNoteById(noteId: Long): Flow<NoteEntity> {
        return localDataSource.getNoteById(noteId)
    }

    override suspend fun insertNote(noteEntity: NoteEntity) {
        withContext(dispatcher) {
            localDataSource.insertNote(noteEntity)
        }
    }

    override suspend fun updateNote(newNote: NoteEntity) {
        withContext(dispatcher) {
            localDataSource.updateNote(newNote)
        }
    }

    override suspend fun deleteNote(note: NoteEntity) {
        withContext(dispatcher) {
            localDataSource.deleteNote(note)
        }
    }
}
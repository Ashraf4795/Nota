package com.nota.data.local

import com.nota.data.local.room.NoteDAO
import com.nota.domain.model.NoteEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource (private val noteDAO: NoteDAO) : LocalDataSourceContract{
    override fun getNotes(): Flow<List<NoteEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun getNoteBy(noteId: Long): NoteEntity {
        TODO("Not yet implemented")
    }
}
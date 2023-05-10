package com.nota.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nota.data.local.room.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDAO {

    @Query("SELECT * FROM note_table")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note_table WHERE note_id=:noteId")
    fun getNoteById(noteId: Long): Flow<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteEntity: NoteEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(newNote: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)
}
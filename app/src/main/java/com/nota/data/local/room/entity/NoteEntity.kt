package com.nota.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    val noteId: Long = 0,
    @ColumnInfo(name = "last_modify_timestamp")
    val lastModifyingTimestamp: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "note_title")
    val noteTitle: String,
    @ColumnInfo(name = "note_text")
    val noteText: String
) {
    fun noteContent(): String {
        return noteTitle.ifBlank { noteText }
    }
}

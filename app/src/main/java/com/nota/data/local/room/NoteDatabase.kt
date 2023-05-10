package com.nota.data.local.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nota.NotaApplication
import com.nota.data.local.room.entity.NoteEntity
import javax.inject.Singleton

@Singleton
@Database(entities = [NoteEntity::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    companion object Initializer {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getNotaDatabase(notaApplication: NotaApplication): NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val database = Room.databaseBuilder(
                    notaApplication.applicationContext,
                    NoteDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = database
                return database
            }
        }
    }

    abstract fun noteDao(): NoteDAO
}
package com.nota.base.di

import androidx.room.Dao
import com.nota.NotaApplication
import com.nota.data.local.LocalDataSource
import com.nota.data.local.LocalDataSourceContract
import com.nota.data.local.room.NoteDAO
import com.nota.data.local.room.NoteDatabase
import com.nota.data.remote.RemoteDataSource
import com.nota.data.repository.NotaRepository
import com.nota.domain.contract.remote.RemoteDataSourceContract
import com.nota.domain.contract.repository.NotaRepositoryContract
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    fun providesLocalDataSource(noteDao: NoteDAO): LocalDataSourceContract {
        return LocalDataSource(noteDao)
    }

    @Provides
    @Singleton
    fun providesRemoteDataSource(): RemoteDataSourceContract {
        return RemoteDataSource()
    }

    @Provides
    @Singleton
    fun providesNoteDao(noteDatabase: NoteDatabase): NoteDAO {
        return noteDatabase.noteDao()
    }

    @Provides
    fun provideNoteDatabase(notaApplication: NotaApplication): NoteDatabase {
        return NoteDatabase.Initializer.getNotaDatabase(notaApplication)
    }

    @Provides
    @Singleton
    fun providesNotaRepository(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        localDataSource: LocalDataSourceContract,
        remoteDataSource: RemoteDataSourceContract
    ): NotaRepositoryContract {
        return NotaRepository(localDataSource, remoteDataSource, dispatcher)
    }
}
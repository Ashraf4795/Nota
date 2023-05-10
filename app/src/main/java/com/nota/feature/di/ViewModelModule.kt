package com.nota.feature.di

import androidx.lifecycle.ViewModel
import com.nota.feature.note_editor.NoteEditorViewModel
import com.nota.feature.note_list.NoteListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(NoteListViewModel::class)
    fun bindsNoteListViewModel(noteListViewModel: NoteListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NoteEditorViewModel::class)
    fun bindsNoteDisplayViewModel(noteDisplayViewModel: NoteEditorViewModel): ViewModel
}
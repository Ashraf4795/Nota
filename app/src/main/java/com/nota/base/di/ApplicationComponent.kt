package com.nota.base.di

import com.nota.NotaApplication
import com.nota.feature.note_editor.NoteEditorFragment
import com.nota.feature.di.ViewModelFactoryModule
import com.nota.feature.di.ViewModelModule
import com.nota.feature.note_list.NoteListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class, ViewModelModule::class, CoroutineModule::class, ViewModelFactoryModule::class
    ]
)
@Singleton
interface ApplicationComponent {

    fun inject(noteListFragment: NoteListFragment)
    fun inject(noteDisplayFragment: NoteEditorFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: NotaApplication): Builder
        fun build(): ApplicationComponent
    }
}
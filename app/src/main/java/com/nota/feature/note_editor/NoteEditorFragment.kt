package com.nota.feature.note_editor

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.nota.NotaApplication
import com.nota.R
import com.nota.TAG
import com.nota.base.BaseFragment
import com.nota.base.di.ApplicationScope
import com.nota.data.local.room.entity.NoteEntity
import com.nota.databinding.FragmentNoteEditorBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteEditorFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val noteDisplayViewModel: NoteEditorViewModel by viewModels { viewModelFactory }
    private val args: NoteEditorFragmentArgs by navArgs()
    private lateinit var binding: FragmentNoteEditorBinding
    private lateinit var passedNoteEntity: NoteEntity
    private var isEditMode: Boolean = false

    @ApplicationScope
    @Inject
    lateinit var applicationScope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteEditorBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteDisplayViewModel.checkNavArgs(args)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                noteDisplayViewModel.noteEntityState.collect {
                    when (it) {
                        AddNewNoteState -> addNewNoteState()
                        is EditNoteState -> editNoteState(it.noteEntity)
                    }
                }
            }
        }
    }


    private fun editNoteState(noteEntity: NoteEntity) {
        isEditMode = true
        passedNoteEntity = noteEntity
        binding.noteEntity = noteEntity
    }

    private fun addNewNoteState() {
        isEditMode = false
        Log.d(TAG, "Add new Note State!")
    }


    override fun onPause() {
        val title = if (binding.noteTitleId.text == null) "No Title" else binding.noteTitleId.text.toString()
        val text = if (binding.noteTextId.text == null) "" else binding.noteTextId.text.toString()

        if (isEditMode) {
            passedNoteEntity = passedNoteEntity.copy(noteText = text, noteTitle = title)
            noteDisplayViewModel.updateNote(applicationScope, passedNoteEntity)
        } else {
            noteDisplayViewModel.save(applicationScope, title, text)
        }
        super.onPause()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as NotaApplication).applicationComponent.inject(this)
    }
}
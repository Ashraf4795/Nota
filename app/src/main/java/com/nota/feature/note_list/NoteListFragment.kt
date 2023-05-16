package com.nota.feature.note_list

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
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.nota.NotaApplication
import com.nota.TAG
import com.nota.base.BaseFragment
import com.nota.data.local.room.ADD_NEW_NOTE
import com.nota.data.local.room.entity.NoteEntity
import com.nota.databinding.FragmentNoteListBinding
import com.nota.domain.state.Empty
import com.nota.domain.state.Error
import com.nota.domain.state.Loading
import com.nota.domain.state.Success
import com.nota.feature.note_list.ui_model.NoteListUiState
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteListFragment : BaseFragment(), OnNoteItemClick, OnItemSwipeEvent<NoteEntity> {
    private val noteListUiState = NoteListUiState(isNoteListEmpty = true)
    private lateinit var binding: FragmentNoteListBinding
    private lateinit var navController: NavController
    private val noteListAdapter: NoteListAdapter by lazy {
        NoteListAdapter(emptyList(), this, this)
    }
    private lateinit var swipeToDeleteHelper: SwipeToDeleteHelper

    private lateinit var itemTouchHelper: ItemTouchHelper

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val noteListViewModel: NoteListViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        navController = findNavController()
        binding = FragmentNoteListBinding.inflate(layoutInflater)
        binding.noteListUiState = this.noteListUiState

        setListeners()
        setSwipeToDeleteHelpers()
        setNoteListRecyclerView()

        noteListViewModel.getAllNotes()
        return binding.root
    }

    private fun setSwipeToDeleteHelpers() {
        swipeToDeleteHelper = SwipeToDeleteHelper(requireContext(), binding.coordinator, noteListAdapter)
        itemTouchHelper = ItemTouchHelper(swipeToDeleteHelper)
    }

    private fun setNoteListRecyclerView() {
        with(binding.noteListRvId) {
            adapter = noteListAdapter
            layoutManager = LinearLayoutManager(this.context)
        }
        itemTouchHelper.attachToRecyclerView(binding.noteListRvId)
    }

    private fun setListeners() {
        binding.addNoteButtonId.setOnClickListener {
            navController.navigate(
                NoteListFragmentDirections.actionNoteListFragmentToNoteDisplayFragment(
                    ADD_NEW_NOTE
                )
            )
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                noteListViewModel.noteListState.collect {
                    when (it) {
                        Loading -> showLoading(true)
                        is Success<*> -> {
                            val noteList = it.data as List<*>
                            renderNoteList(noteList)
                            getNotesDone(noteList.isEmpty())
                        }

                        Empty -> {
                            getNotesDone(isNoteListEmpty = true)
                        }

                        is Error -> {
                            getNotesDone(isNoteListEmpty = true)
                        }

                    }
                }
            }

        }
    }

    private fun renderNoteList(noteList: List<*>) {
        noteListAdapter.submitNewList(noteList as List<NoteEntity>)
    }

    private fun getNotesDone(isNoteListEmpty: Boolean) {
        showLoading(false)
        binding.noteListUiState = NoteListUiState(isNoteListEmpty)
        Log.d(TAG, "getNoteDone: $isNoteListEmpty")
    }

    private fun showLoading(shouldShow: Boolean) {
        binding.progressBarId.visibility = if (shouldShow) View.VISIBLE else View.GONE
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as NotaApplication).applicationComponent.inject(this)
    }

    override fun onClick(note: NoteEntity) {
        navController.navigate(
            NoteListFragmentDirections.actionNoteListFragmentToNoteDisplayFragment(
                note.noteId
            )
        )
    }

    override fun onItemRemoved(note: NoteEntity) {
        lifecycleScope.launch {
            noteListViewModel.removeNoteAt(note)
        }
    }

    override fun onUnDoItemRemove(item: NoteEntity) {
        lifecycleScope.launch {
            noteListViewModel.restoreNote(item)
        }
    }

}
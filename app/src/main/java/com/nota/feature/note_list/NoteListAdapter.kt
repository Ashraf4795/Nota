package com.nota.feature.note_list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.nota.data.local.room.entity.NoteEntity
import com.nota.databinding.NoteItemBinding
import com.nota.utils.addIfUnique

class NoteListAdapter(
    private val noteList: List<NoteEntity>,
    private val onNoteItemClick: OnNoteItemClick
) : RecyclerView.Adapter<NoteListAdapter.NoteItemViewHolder>() {

    private val mutableNoteList = noteList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteItemViewHolder {
        val noteItemBinding =
            NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteItemViewHolder(noteItemBinding)
    }

    override fun getItemCount(): Int = mutableNoteList.size

    override fun onBindViewHolder(holder: NoteItemViewHolder, position: Int) {
        val noteEntity = mutableNoteList[position]
        holder.bind(noteEntity)
    }

    fun submitNewList(noteList: List<NoteEntity>) {
        mutableNoteList.clear()
        mutableNoteList.addAll(noteList)
        notifyDataSetChanged()
    }

    inner class NoteItemViewHolder(private val binding: NoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: NoteEntity) {
            binding.noteEntity = note
            binding.noteItemRoot.setOnClickListener {
                onNoteItemClick.onClick(note)
            }
        }
    }
}

interface OnNoteItemClick {
    fun onClick(note: NoteEntity)
}
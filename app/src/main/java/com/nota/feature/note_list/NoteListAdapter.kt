package com.nota.feature.note_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nota.data.local.room.entity.NoteEntity
import com.nota.databinding.NoteItemBinding

class NoteListAdapter(
    private val noteList: List<NoteEntity>,
    private val onNoteItemClick: OnNoteItemClick,
    private val onItemSwipeEvent: OnItemSwipeEvent<NoteEntity>
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

    fun removeItemAt(itemPosition: Int) {
        val removedItem = mutableNoteList[itemPosition]
        notifyItemRemoved(itemPosition)
        onItemSwipeEvent.onItemRemoved(removedItem)
    }

    fun restoreLastDeletedItem(note: NoteEntity, position: Int) {
        onItemSwipeEvent.onUnDoItemRemove(note)
    }

    fun getData(): List<NoteEntity> {
        return mutableNoteList.toList()
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

interface  OnItemSwipeEvent <T> {
    fun onItemRemoved(item: T)
    fun onUnDoItemRemove(item: T)
}
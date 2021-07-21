package hr.trailovic.notesqkeeper.view.main

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.trailovic.notesqkeeper.databinding.ItemNoteBinding
import hr.trailovic.notesqkeeper.model.NoteSelectable

class NotesAdapter(private val listener: OnNoteItemInteraction) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val notes = mutableListOf<NoteSelectable>()

    fun setItems(list: List<NoteSelectable>) {
        with(notes) {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemNoteBinding =
            ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(itemNoteBinding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount(): Int = notes.size

    inner class NoteViewHolder(private val itemNoteBinding: ItemNoteBinding) :
        RecyclerView.ViewHolder(itemNoteBinding.root) {
        init {
            itemNoteBinding.root.setOnClickListener {
                listener.onClick(notes[layoutPosition])
            }
            itemNoteBinding.root.setOnLongClickListener {
                listener.onLongClick(notes[layoutPosition])
                true
            }
        }

        fun bind(note: NoteSelectable) {
            itemNoteBinding.tvTitle.text = note.note.title
            itemNoteBinding.tvBody.text = note.note.body
            itemNoteBinding.root.setBackgroundColor(
                if (note.isSelected) Color.GRAY else Color.WHITE
            )
        }
    }
}

interface OnNoteItemInteraction {
    fun onClick(note: NoteSelectable)
    fun onLongClick(note: NoteSelectable)
}
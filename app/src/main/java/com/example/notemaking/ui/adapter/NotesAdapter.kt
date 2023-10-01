package com.example.notemaking.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notemaking.data.local.models.Todo
import com.example.notemaking.R

class NotesAdapter(val onClickListener: OnClickListener) : ListAdapter<Todo, NotesAdapter.ViewHolder>(NoteDiffCallback()) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.noteTitle)
        val contentTextView: TextView = itemView.findViewById(R.id.noteContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = getItem(position)
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content
        holder.itemView.setOnClickListener {
            onClickListener.onClicklistener(note, position)
        }

    }

    private class NoteDiffCallback : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }

    interface OnClickListener {
        fun onClicklistener(id: Todo, position: Int)
    }

}

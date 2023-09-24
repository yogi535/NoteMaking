package com.example.notemaking.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notemaking.data.local.repository.NoteRepository

class NoteViewModelFactory(private val noteRepository: NoteRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModal::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModal(noteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}






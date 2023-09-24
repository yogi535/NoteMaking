package com.example.notemaking.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.notemaking.data.local.models.Todo
import com.example.notemaking.ui.viewModel.NoteViewModal
import com.example.notemaking.ui.viewModel.NoteViewModelFactory
import com.example.notemaking.NotesApplication
import com.example.notemaking.data.local.repository.NoteRepository
import com.example.notemaking.databinding.FragmentCreateNoteBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateNoteFragment : BaseFragment() {

    private var _binding: FragmentCreateNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var noteViewModel: NoteViewModal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("Testing", "onViewCreated CreateNoteFragment")

        val notesApplication = requireContext().applicationContext as NotesApplication
        val notesDao = notesApplication.noteDatabase.getNoteDao()

        val noteViewModelFactory = NoteViewModelFactory(NoteRepository(notesDao))
        noteViewModel = ViewModelProvider(this, noteViewModelFactory)[NoteViewModal::class.java]

        binding.buttonSave.setOnClickListener {
            val title = binding.editTextTitle.text.toString().trim()
            val content = binding.editTextContent.text.toString().trim()

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(requireActivity(), "Please Enter title and Content", Toast.LENGTH_SHORT).show()
            } else {
                val note = Todo(title = title, content = content)

                GlobalScope.launch {
                    noteViewModel.insertNote(note)
                }

                closeFragment()
            }
        }
    }

    private fun closeFragment() {
        val fragmentManager = requireActivity().supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
        } else {
            // If there are no fragments in the back stack, you can finish the activity instead.
            requireActivity().finish()
        }
    }

//    private fun closeFragment() {
//        requireActivity().supportFragmentManager.beginTransaction()
//            .remove(this)
//            .commit()
//    }

}

package com.example.notemaking.ui

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.notemaking.R
import com.example.notemaking.data.local.models.Todo
import com.example.notemaking.databinding.FragmentCreateNoteBinding
import com.example.notemaking.ui.viewModel.NoteViewModal
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CreateNoteFragment : BaseFragment() {

    private var _binding: FragmentCreateNoteBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var noteViewModel: NoteViewModal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data: Parcelable? = if (Build.VERSION.SDK_INT >= 33) {
            arguments?.getParcelable<Todo>(getString(R.string.todo))
        } else {
            arguments?.getParcelable<Todo>(getString(R.string.todo)) ?: null
        }

       if (data != null) {
           (data as Todo).apply {
               binding.editTextTitle.setText(title.toString())
               binding.editTextContent.setText(content.toString())
           }
           binding.buttonSave.visibility = View.GONE
           binding.buttonEdit.visibility = View.VISIBLE
           binding.buttonDelete.visibility = View.VISIBLE
       } else {
           binding.buttonEdit.visibility = View.GONE
           binding.buttonDelete.visibility = View.GONE
           binding.buttonSave.visibility = View.VISIBLE
       }


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

        binding.buttonEdit.setOnClickListener {
            val title = binding.editTextTitle.text.toString().trim()
            val content = binding.editTextContent.text.toString().trim()

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(requireActivity(), "Please Enter title and Content", Toast.LENGTH_SHORT).show()
            } else {
                val note = Todo(id = (data as Todo).id, title = title, content = content)

                GlobalScope.launch {
                    noteViewModel.updateNote(note)
                }

                closeFragment()
            }
        }

        binding.buttonDelete.setOnClickListener {
            val title = binding.editTextTitle.text.toString().trim()
            val content = binding.editTextContent.text.toString().trim()

            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Yes") { dialog, which ->
                    val note = Todo(id = (data as Todo).id, title = title, content = content)
                    GlobalScope.launch {
                        noteViewModel.deleteNote(note)
                    }

                    closeFragment()

                }
                .setNegativeButton("No") { dialog, which ->
                }
                .create()

            dialog.show()
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
}

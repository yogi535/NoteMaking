package com.example.notemaking.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.notemaking.R
import com.example.notemaking.data.local.models.Todo
import com.example.notemaking.databinding.FragmentCreateNoteBinding
import com.example.notemaking.ui.viewModel.NoteViewModal
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CreateNoteFragment : BaseFragment() {

    private var _binding: FragmentCreateNoteBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var noteViewModel: NoteViewModal

    private var todo: Todo? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        todo = arguments?.getParcelable(getString(R.string.todo))

        setupViews()
        setupButtonClickListeners()
    }

    private fun setupViews() {
        with(binding) {
            buttonSave.visibility = if (todo == null) View.VISIBLE else View.GONE
            buttonEdit.visibility = if (todo != null) View.VISIBLE else View.GONE
            buttonDelete.visibility = if (todo != null) View.VISIBLE else View.GONE

            todo?.let {
                editTextTitle.setText(it.title)
                editTextContent.setText(it.content)
            }
        }
    }

    private fun setupButtonClickListeners() {
        binding.buttonSave.setOnClickListener { saveOrUpdateNote() }
        binding.buttonEdit.setOnClickListener { saveOrUpdateNote() }
        binding.buttonDelete.setOnClickListener { showDeleteConfirmationDialog() }
    }

    private fun saveOrUpdateNote() {
        val title = binding.editTextTitle.text.toString().trim()
        val content = binding.editTextContent.text.toString().trim()

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(requireActivity(), "Please Enter title and Content", Toast.LENGTH_SHORT).show()
            return
        }

        val note = todo?.copy(title = title, content = content) ?: Todo(title = title, content = content)

        lifecycleScope.launch {
            if (todo == null) {
                noteViewModel.insertNote(note)
            } else {
                noteViewModel.updateNote(note)
            }
        }

        closeFragment()
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete")
            .setMessage("Are you sure you want to delete this item?")
            .setPositiveButton("Yes") { dialog, _ ->
                val note = todo?.copy() // Make a copy of the original note to avoid modifying it
                note?.let {
                    lifecycleScope.launch {
                        noteViewModel.deleteNote(it)
                    }
                }
                closeFragment()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun closeFragment() {
        val fragmentManager = requireActivity().supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
        } else {
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

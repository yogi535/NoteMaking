package com.example.notemaking.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notemaking.data.local.models.Todo
import com.example.notemaking.ui.adapter.NotesAdapter
import com.example.notemaking.R
import com.example.notemaking.databinding.FragmentShowAllNotesBinding
import com.example.notemaking.ui.viewModel.NoteViewModal
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ShowAllNotesFragment : BaseFragment(), NotesAdapter.OnClickListener {

    private var _binding: FragmentShowAllNotesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var noteViewModel: NoteViewModal

    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("Testing", "onCreate ShowAllNotesFragment")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShowAllNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("Testing", "onViewCreated ShowAllNotesFragment")
        initializeViewModel()
        setupRecyclerView()
        setupObservers()
        setupFabClickListener()
    }

    private fun initializeViewModel() {
        lifecycleScope.launch { noteViewModel.getAllNotes() }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        notesAdapter = NotesAdapter(this)
        binding.recyclerView.adapter = notesAdapter
    }

    private fun setupObservers() {
        noteViewModel.notesList.observe(viewLifecycleOwner, Observer { notesList ->
            if (notesList.isEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.txtEmptyNotes.visibility = View.VISIBLE
            } else {
                showNotes(notesList)
                binding.recyclerView.visibility = View.VISIBLE
                binding.txtEmptyNotes.visibility = View.GONE
            }
        })
    }

    private fun showNotes(notesList: List<Todo>) {
        notesAdapter.submitList(notesList)
    }

    private fun setupFabClickListener() {
        binding.floatingActionButton.setOnClickListener {
            openCreateNoteFragment(edit = false)
        }
    }

    private fun openCreateNoteFragment(edit: Boolean = false, todo: Todo? = null) {
        val createNoteFragment = CreateNoteFragment()
        if (edit) {
            val bundle = Bundle()
            bundle.putParcelable(getString(R.string.todo), todo)
            createNoteFragment.arguments = bundle
        }
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, createNoteFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClicklistener(todo: Todo, position: Int) {
        Log.e("TodoInfo", "${todo.id} title = ${todo.title} content = ${todo.content} ")
        openCreateNoteFragment(edit = true, todo = todo)
    }
}

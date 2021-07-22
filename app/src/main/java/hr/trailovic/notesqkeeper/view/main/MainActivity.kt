package hr.trailovic.notesqkeeper.view.main

import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import hr.trailovic.notesqkeeper.base.BaseActivity
import hr.trailovic.notesqkeeper.databinding.ActivityMainBinding
import hr.trailovic.notesqkeeper.model.NoteSelectable
import hr.trailovic.notesqkeeper.model.consume
import hr.trailovic.notesqkeeper.view.add.AddActivity
import hr.trailovic.notesqkeeper.viewmodel.NotesViewModel

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModelNotes: NotesViewModel by viewModels()

    private val adapter = NotesAdapter(object : OnNoteItemInteraction {
        override fun onClick(note: NoteSelectable) {
            viewModelNotes.shortClick(note)
        }

        override fun onLongClick(note: NoteSelectable) {
            viewModelNotes.longClick(note)
        }
    })

    override fun getBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun setup() {
        setListeners()
        setNotesList()
        setBind()
    }

    private fun setBind() {
        viewModelNotes.notesLD.observe(this) {
            adapter.setItems(it)
        }

        viewModelNotes.addButtonVisible.observe(this) {
            binding.fabAddTask.visibility = setVisibility(it)
            binding.fabDeleteSelection.visibility = setVisibility(it.not())
        }

        viewModelNotes.noteToEdit.observe(this) { oneTimeEventNote ->
            oneTimeEventNote.consume { note ->
                val newIntent = AddActivity.newIntent(this, note)
                startActivity(newIntent)
            }
        }
    }

    private fun setNotesList() {
        binding.rvNotes.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvNotes.adapter = adapter
    }

    private fun setListeners() {
        binding.fabAddTask.setOnClickListener {
            val newIntent = AddActivity.newIntent(this)
            startActivity(newIntent)
        }
        binding.fabDeleteSelection.setOnClickListener {
            viewModelNotes.deleteSelected()
        }
    }

    private fun setVisibility(visible: Boolean) = if (visible) View.VISIBLE else View.GONE

}
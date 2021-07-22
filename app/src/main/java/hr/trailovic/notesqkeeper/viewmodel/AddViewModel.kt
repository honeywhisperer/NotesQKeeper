package hr.trailovic.notesqkeeper.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.trailovic.notesqkeeper.model.Note
import hr.trailovic.notesqkeeper.repo.NotesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(private val repo: NotesRepository) : ViewModel() {
    private var loadedNote: Note? = null

    fun loadedNote(note: Note) {
        loadedNote = note
    }

    fun add(noteTitle: String, noteBody: String) {
        viewModelScope.launch {
            repo.add(Note(noteTitle, noteBody))
        }
    }

    fun delete() {
        loadedNote?.let { note ->
            _delete(note)
        }
    }

    fun update(noteTitle: String, noteBody: String) {
        loadedNote?.let { note ->
            val newNote = note.copy(title = noteTitle, body = noteBody)
            _update(newNote)
        }
    }

    private fun _delete(note: Note) {
        viewModelScope.launch {
            repo.delete(note)
        }
    }

    private fun _update(note: Note) {
        viewModelScope.launch {
            repo.update(note)
        }
    }

}
package hr.trailovic.notesqkeeper.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.trailovic.notesqkeeper.model.Note
import hr.trailovic.notesqkeeper.model.NoteSelectable
import hr.trailovic.notesqkeeper.repo.NotesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(private val repo: NotesRepository): ViewModel() {
    private var _noteToLoad = MutableLiveData<NoteSelectable>()
    val noteToLoad: LiveData<NoteSelectable> = _noteToLoad

    fun loadToShow(note: NoteSelectable){
        _noteToLoad.value = note
    }

    fun add(noteTitle: String, noteBody: String){
        viewModelScope.launch {
            repo.add(Note(noteTitle, noteBody))
        }
    }

}
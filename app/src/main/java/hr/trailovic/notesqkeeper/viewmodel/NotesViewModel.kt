package hr.trailovic.notesqkeeper.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.trailovic.notesqkeeper.model.Note
import hr.trailovic.notesqkeeper.model.NoteSelectable
import hr.trailovic.notesqkeeper.repo.NotesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val repo: NotesRepository) : ViewModel() {

//    private var _allNotesMLD = MutableLiveData<List<NoteSelectable>>()
//    val allNotesLD: LiveData<List<NoteSelectable>> = _allNotesMLD

    //  <<< Feed Main Activity with data
    private val _notesFromDb : LiveData<List<Note>>
        get() {//todo: fix
            return repo.getAll()
        }

    private var _selectedNotes = MutableLiveData<Set<Note>>()
    private var _merged = MediatorLiveData<List<NoteSelectable>>()

    val notesLD: LiveData<List<NoteSelectable>> = _merged
    //  >>> Feed Main Activity with Notes data

    // <<< Control switching between Add and Delete buttons visibilities
    private var _addButtonVisible = MutableLiveData<Boolean>(_selectedNotes.value.isNullOrEmpty())
    val addButtonVisible: LiveData<Boolean> = _addButtonVisible
    // >>> Control switching between Add and Delete buttons visibilities

    init {
//        getAll()

        _merged.addSource(_notesFromDb) {
//            val newList = merge(_notesFromDb.value, _selectedNotes.value)
            val newList = merge(it, _selectedNotes.value)
            _merged.postValue(newList)
        }

        _merged.addSource(_selectedNotes) {
//            val newList = merge(_notesFromDb.value, _selectedNotes.value)
            val newList = merge(_notesFromDb.value, it)
            _merged.postValue(newList)
        }
    }

    private fun updateValues(notes: List<NoteSelectable>, selected: Set<Note>) {
        notes.forEach {
            it.isSelected = it.note in selected
        }
    }

//    private fun getAll() {
//        _notesFromDb = repo.getAll() as MutableLiveData<List<Note>>
//    }

//    fun add(title: String, body: String) {
//        viewModelScope.launch {
//            repo.add(Note(title, body))
//        }
//    }


    /*If set of selected notes is empty, show the current note in AddActivity.
    Otherwise, add or remove it from the selection.*/
    fun shortClick(note: NoteSelectable) {
        if (_selectedNotes.value.isNullOrEmpty()) {
            // open the note in another activity
        } else {
            changeIsSelectedStatus(note.note, _selectedNotes)
        }
    }


    /*If no note is selected, add the current one to the selection;
    after this, every note that is shortClick-ed will be added or removed from the list of selected items.
    (long clicks are the same as short)
    Selected items can be deleted - when set of selected items is not empty, Add button disappears and
    Delete button shows up.*/

    fun longClick(note: NoteSelectable) {
        if (_selectedNotes.value.isNullOrEmpty()) {
            changeIsSelectedStatus(note.note, _selectedNotes)
        } else {
            // todo
            // ignore this option, for now;
            // come up with some useful functionality later.
        }
    }

    private fun changeIsSelectedStatus(note: Note, selection: MutableLiveData<Set<Note>>) {
        selection.value?.let {
            val setOfNotes = it as MutableSet
            if (note in it) {
                setOfNotes.remove(note)
            } else {
                setOfNotes.add(note)
            }
            selection.postValue(setOfNotes)
        } ?: run {
            selection.postValue(setOf(note))
        }
    }

    fun deleteSelected() {
        viewModelScope.launch {
            _selectedNotes.value?.let { set ->
                set.forEach { note ->
                    repo.delete(note)
                    val setOfNotes = set as MutableSet
                    setOfNotes.remove(note)
                    _selectedNotes.value = setOfNotes
                }
            }
        }
    }

    private fun merge(notes: List<Note>?, statuses: Set<Note>?): List<NoteSelectable> {
        val selectables = mutableListOf<NoteSelectable>()
        notes?.let { list ->
            list.forEach { note ->
                selectables.add(NoteSelectable(note, statuses?.contains(note) ?: false))
            }
        }
        return selectables
    }
}

package hr.trailovic.notesqkeeper.viewmodel

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.trailovic.notesqkeeper.addOrRemove
import hr.trailovic.notesqkeeper.model.Note
import hr.trailovic.notesqkeeper.model.NoteSelectable
import hr.trailovic.notesqkeeper.model.OneTimeEvent
import hr.trailovic.notesqkeeper.model.toOneTimeEvent
import hr.trailovic.notesqkeeper.repo.NotesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = ":::nVM"

@HiltViewModel
class NotesViewModel @Inject constructor(private val repo: NotesRepository) : ViewModel() {

    /**Feed Main Activity with data*/

    private val _notesFromDb: LiveData<List<Note>> = repo.getAll()

    private var _selectedNotes = MutableLiveData<Set<Note>>()

    private var _merged = MediatorLiveData<List<NoteSelectable>>()

    val notesLD: LiveData<List<NoteSelectable>> = _merged


    /**Control switching between Add and Delete buttons visibilities*/

    val addButtonVisible: LiveData<Boolean> = Transformations.switchMap(_selectedNotes) {
        if (it.isNullOrEmpty())
            return@switchMap MutableLiveData<Boolean>(true)
        else
            return@switchMap MutableLiveData<Boolean>(false)
    }


    /**Open in AddActivity for detailed view / editing*/

    private var _noteToEdit = MutableLiveData<OneTimeEvent<Note>>()
    val noteToEdit: LiveData<OneTimeEvent<Note>> = _noteToEdit


    init {
        /**_merged - Build LiveData sensitive to both: notes change and selection change*/

        _merged.addSource(_notesFromDb) { list ->
            val newList = merge(list, _selectedNotes.value)
            _merged.postValue(newList)
        }

        _merged.addSource(_selectedNotes) { set ->
            val newList = merge(_notesFromDb.value, set)
            _merged.postValue(newList)
        }
    }


    /**If set of selected notes is empty, show the current note in AddActivity.
    Otherwise, add or remove it from the selection.*/
    fun shortClick(note: NoteSelectable) {
        if (_selectedNotes.value.isNullOrEmpty()) {
            _noteToEdit.postValue(note.note.toOneTimeEvent())
        } else {
            val newSet = changeIsSelectedStatus(note.note, _selectedNotes.value)
            _selectedNotes.postValue(newSet)
        }
    }


    /**If no note is selected, add the current one to the selection;
    after this, every note that is shortClick-ed will be added or removed from the list of selected items.
    Selected items can be deleted - when set of selected items is not empty, Add button disappears and
    Delete button shows up.*/
    fun longClick(note: NoteSelectable) {
        val newSet = changeIsSelectedStatus(note.note, _selectedNotes.value)
        _selectedNotes.postValue(newSet)
    }


    /***/
    private fun changeIsSelectedStatus(
        note: Note,
        selection: Set<Note>?
    ): Set<Note> {
        val setOfNotes = selection?.toMutableSet() ?: mutableSetOf()
        setOfNotes.addOrRemove<Note>(note)
        return setOfNotes
    }

    /***/
    fun deleteSelected() {
        viewModelScope.launch {
            _selectedNotes.value?.let { set ->
                set.forEach { note ->
                    repo.delete(note)
                }
                _selectedNotes.postValue(emptySet())
            }
        }
    }

    /***/
    private fun merge(notes: List<Note>?, statuses: Set<Note>?): List<NoteSelectable> {
        Log.d(TAG, "merge: notes: ${notes?.size} set: ${statuses?.size}")
        val selectables = mutableListOf<NoteSelectable>()
        notes?.let { list ->
            list.forEach { note ->
                selectables.add(NoteSelectable(note, statuses?.contains(note) ?: false))
            }
        }
        return selectables
    }
}

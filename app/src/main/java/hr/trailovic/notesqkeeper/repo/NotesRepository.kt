package hr.trailovic.notesqkeeper.repo

import androidx.lifecycle.LiveData
import hr.trailovic.notesqkeeper.model.Note

interface NotesRepository {
    suspend fun add(note: Note)

    suspend fun update(note: Note)

    suspend fun delete(note: Note)

    fun getAll(): LiveData<List<Note>>
}
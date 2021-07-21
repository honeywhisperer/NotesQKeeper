package hr.trailovic.notesqkeeper.repo

import androidx.lifecycle.LiveData
import hr.trailovic.notesqkeeper.db.AppDatabase
import hr.trailovic.notesqkeeper.db.NoteDao
import hr.trailovic.notesqkeeper.model.Note

class NotesRepositoryImpl(appDatabase: AppDatabase) : NotesRepository {

    private val noteDao = appDatabase.noteDao()

    override suspend fun add(note: Note) {
        noteDao.add(note)
    }

    override suspend fun update(note: Note) {
        noteDao.update(note)
    }

    override suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

    override fun getAll(): LiveData<List<Note>> {
        return noteDao.getAll()
    }
}
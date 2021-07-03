package hr.trailovic.notesqkeeper.db

import androidx.room.*
import hr.trailovic.notesqkeeper.model.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(note: Note)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("DELETE FROM Note")
    suspend fun deleteAll()

    @Query("SELECT * FROM Note")
    suspend fun getAll()
}
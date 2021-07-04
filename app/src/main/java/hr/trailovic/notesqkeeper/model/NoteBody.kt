package hr.trailovic.notesqkeeper.model

import androidx.room.Entity
import java.util.*

@Entity
abstract class NoteBody(val uuid : String = UUID.randomUUID().toString()){
    abstract fun toSimpleString(): String
}
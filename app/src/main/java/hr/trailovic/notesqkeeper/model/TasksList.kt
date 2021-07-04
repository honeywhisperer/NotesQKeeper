package hr.trailovic.notesqkeeper.model

import androidx.room.Entity

/**
 * List of Tasks (one option for a Note Body)
 * */
@Entity
data class TasksList(
    val list: List<Task> = emptyList<Task>()
): NoteBody() {
    override fun toSimpleString(): String {
        val stringBuilder = StringBuilder()
        list.forEach {
            stringBuilder.append(it.description + '\n')
        }
        return stringBuilder.toString()
    }

    companion object{
        fun fromSimpleString(input: String): TasksList{
            val list = input.split('\n').map { Task(it, false) }
            return TasksList(list)
        }
    }
}

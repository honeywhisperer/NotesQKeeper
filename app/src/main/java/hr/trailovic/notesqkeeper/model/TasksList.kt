package hr.trailovic.notesqkeeper.model
/**
 * List of Tasks (one option for a Note Body)
 * */
data class TasksList(
    val list: List<Task>
): NoteBody

package hr.trailovic.notesqkeeper

import hr.trailovic.notesqkeeper.model.Note
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ExtensionsKtTest {
    lateinit var sampleSetOfNumbers: MutableSet<Int>
    lateinit var sampleSetOfNotes: MutableSet<Note>
    lateinit var emptySetOfNotes: MutableSet<Note>
    lateinit var setNote1: Note
    lateinit var setNote2: Note
    lateinit var anotherNote: Note

    @Before
    fun setup() {
        sampleSetOfNumbers = mutableSetOf(1, 2, 3)

        setNote1 = Note("Wash car", "")
        setNote2 = Note("Walk the dog", "Evening. Park")
        anotherNote = Note("Take out trash", "Plastic, glass, paper")
        sampleSetOfNotes = mutableSetOf(setNote1, setNote2)
        emptySetOfNotes = mutableSetOf()
    }

    @Test
    fun shouldAddNewElementToSet() {
        sampleSetOfNumbers.addOrRemove(4)
        assertArrayEquals(setOf(1, 2, 3, 4).toIntArray(), sampleSetOfNumbers.toIntArray())
    }

    @Test
    fun shouldRemoveExistingElementFromSet() {
        sampleSetOfNumbers.addOrRemove(3)
        assertArrayEquals(setOf(1, 2).toIntArray(), sampleSetOfNumbers.toIntArray())
    }

    @Test
    fun shouldAddNewNote(){
        sampleSetOfNotes.addOrRemove(anotherNote)
        assertArrayEquals(setOf(setNote1, setNote2, anotherNote).toTypedArray(), sampleSetOfNotes.toTypedArray())
    }

    @Test
    fun shouldRemoveExistingNote(){
        sampleSetOfNotes.addOrRemove(setNote2)
        assertArrayEquals(setOf(setNote1).toTypedArray(), sampleSetOfNotes.toTypedArray())
    }

    @Test
    fun shouldAddNewNoteToEmptySet(){
        emptySetOfNotes.addOrRemove(anotherNote)
        assertArrayEquals(setOf(anotherNote).toTypedArray(), emptySetOfNotes.toTypedArray())
    }
}
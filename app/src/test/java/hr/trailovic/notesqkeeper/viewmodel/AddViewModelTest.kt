package hr.trailovic.notesqkeeper.viewmodel

import hr.trailovic.notesqkeeper.model.Note
import hr.trailovic.notesqkeeper.repo.NotesRepository
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddViewModelTest{
    private lateinit var addViewModel: AddViewModel

    @Mock
    lateinit var repository: NotesRepository

    @Before
    fun setup(){
        addViewModel = AddViewModel(repository)
    }

    @Test
    fun canSaveNoteWithoutTitle(){
        val newNote = addViewModel.add("", "")
        assertEquals(newNote, Note("", ""))

    }
}
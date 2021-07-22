package hr.trailovic.notesqkeeper.view.add

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hr.trailovic.notesqkeeper.base.BaseActivity
import hr.trailovic.notesqkeeper.databinding.ActivityAddBinding
import hr.trailovic.notesqkeeper.model.Note
import hr.trailovic.notesqkeeper.viewmodel.AddViewModel

@AndroidEntryPoint
class AddActivity : BaseActivity<ActivityAddBinding>() {

    private val viewModel: AddViewModel by viewModels()

    private var noteToShow: Note? = null

    override fun getBinding(): ActivityAddBinding = ActivityAddBinding.inflate(layoutInflater)

    override fun setup() {
        extractNote()
        setListeners()
        setButtonsVisibility()
    }

    private fun setButtonsVisibility() {
        //todo
        setVisibilityForButton(binding.btnCancel, View.VISIBLE)
        noteToShow?.let {
            // option - edit note
            setVisibilityForButton(binding.btnSave, View.GONE)
            setVisibilityForButton(binding.btnUpdate, View.VISIBLE)
            setVisibilityForButton(binding.btnDelete, View.VISIBLE)
        } ?: run {
            // option - add new note
            setVisibilityForButton(binding.btnSave, View.VISIBLE)
            setVisibilityForButton(binding.btnUpdate, View.GONE)
            setVisibilityForButton(binding.btnDelete, View.GONE)
        }
    }

    private fun extractNote() {
        noteToShow = intent.getParcelableExtra<Note>(ARG_EXTRA_NOTE)
        noteToShow?.let {
            binding.etTitle.setText(it.title)
            binding.etBody.setText(it.body)
            viewModel.loadedNote(it)
        }
    }

    private fun setListeners() {
        binding.btnSave.setOnClickListener {
            val titleIsOk = checkIfTextFieldIsNotBlank(binding.etTitle)
            val bodyIsOk = checkIfTextFieldIsNotBlank(binding.etBody)
            if (titleIsOk && bodyIsOk) {
                viewModel.add(binding.etTitle.text.toString(), binding.etBody.text.toString())
                finish()
            }
        }

        binding.btnUpdate.setOnClickListener {
            val titleIsOk = checkIfTextFieldIsNotBlank(binding.etTitle)
            val bodyIsOk = checkIfTextFieldIsNotBlank(binding.etBody)
            if (titleIsOk && bodyIsOk) {
                viewModel.update(binding.etTitle.text.toString(), binding.etBody.text.toString())
                finish()
            }
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.btnDelete.setOnClickListener {
            viewModel.delete()
            finish()
        }
    }

    private fun checkIfTextFieldIsNotBlank(field: EditText): Boolean {
        return if (field.text.isBlank()) {
            field.error = "Required"
            false
        } else {
            true
        }
    }

    private fun setVisibilityForButton(button: Button, visibility: Int) {
        button.visibility = visibility
    }

    companion object {
        private const val ARG_EXTRA_NOTE = "extra.note"
        fun newIntent(context: Context) = Intent(context, AddActivity::class.java)
        fun newIntent(context: Context, note: Note) =
            Intent(context, AddActivity::class.java).apply {
                putExtra(ARG_EXTRA_NOTE, note)
            }
    }
}
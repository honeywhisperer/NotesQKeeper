package hr.trailovic.notesqkeeper.view.add

import android.content.Context
import android.content.Intent
import android.widget.EditText
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hr.trailovic.notesqkeeper.base.BaseActivity
import hr.trailovic.notesqkeeper.databinding.ActivityAddBinding
import hr.trailovic.notesqkeeper.viewmodel.AddViewModel

@AndroidEntryPoint
class AddActivity : BaseActivity<ActivityAddBinding>() {

    private val viewModel: AddViewModel by viewModels()

    override fun getBinding(): ActivityAddBinding = ActivityAddBinding.inflate(layoutInflater)

    override fun setup() {
        setListeners()
        setBind()

    }

    private fun setBind() {
        viewModel.noteToLoad.observe(this) {
            binding.etTitle.setText(it.note.title)
            binding.etBody.setText(it.note.body)
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
    }

    private fun checkIfTextFieldIsNotBlank(field: EditText): Boolean {
        return if (field.text.isBlank()) {
            field.error = "Required"
            false
        } else {
//            field.error = ""
            true
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, AddActivity::class.java)
    }


}
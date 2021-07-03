package hr.trailovic.notesqkeeper.view.add

import dagger.hilt.android.AndroidEntryPoint
import hr.trailovic.notesqkeeper.base.BaseActivity
import hr.trailovic.notesqkeeper.databinding.ActivityAddBinding

@AndroidEntryPoint
class AddActivity : BaseActivity<ActivityAddBinding>() {
    override fun getBinding(): ActivityAddBinding = ActivityAddBinding.inflate(layoutInflater)

    override fun setup() {
        //TODO("Not yet implemented")
    }

}
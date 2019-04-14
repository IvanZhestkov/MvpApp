package com.itis.android.mvpapp.presentation.ui.main.loadtask

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.base.BaseFragment
import javax.inject.Inject
import javax.inject.Provider
import com.nbsp.materialfilepicker.MaterialFilePicker
import com.nbsp.materialfilepicker.ui.FilePickerActivity
import kotlinx.android.synthetic.main.fragment_load_task.*

class LoadTaskFragment : BaseFragment(), LoadTaskView {

    companion object {
        fun getInstance() = LoadTaskFragment()
    }

    override val mainContentLayout = R.layout.fragment_load_task

    override val enableBackArrow = true

    override val toolbarTitle = R.string.toolbar_task

    @InjectPresenter
    lateinit var presenter: LoadTaskPresenter

    @Inject
    lateinit var presenterProvider: Provider<LoadTaskPresenter>

    @ProvidePresenter
    fun providePresenter(): LoadTaskPresenter = presenterProvider.get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_load_task.setOnClickListener {
            MaterialFilePicker()
                    .withActivity(requireActivity())
                    .withRequestCode(1)
                    .withHiddenFiles(true) // Show hidden files and folders
                    .start()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val filePath = data?.getStringExtra(FilePickerActivity.RESULT_FILE_PATH)
            Toast.makeText(activity, filePath, Toast.LENGTH_SHORT).show()
        }
    }
}
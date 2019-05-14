package com.itis.android.mvpapp.presentation.ui.student.uploadtask

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.base.BaseFragment
import javax.inject.Inject
import javax.inject.Provider
import kotlinx.android.synthetic.main.fragment_new_task.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.itis.android.mvpapp.presentation.model.FileModel
import com.itis.android.mvpapp.router.initparams.NewTaskInitParams
import kotlinx.android.synthetic.main.layout_progress_error.*
import com.itis.android.mvpapp.presentation.util.extensions.*
import com.redmadrobot.inputmask.MaskedTextChangedListener
import kotlinx.android.synthetic.main.fragment_load_task_student.*


class NewStudentTaskFragment : BaseFragment(), NewStudentTaskView {

    companion object {
        private const val REQUEST_CODE_PICK_FILE = 1

        fun getInstance(initParams: NewTaskInitParams): NewStudentTaskFragment {
            return NewStudentTaskFragment().also {
                it.putInitParams(initParams)
            }
        }
    }

    override val mainContentLayout = R.layout.fragment_load_task_student

    override val enableBackArrow = true

    override val toolbarTitle = R.string.toobar_new_task

    override val menu: Int?
        get() = null

    @InjectPresenter
    lateinit var presenter: NewStudentTaskPresenter

    @Inject
    lateinit var presenterProvider: Provider<NewStudentTaskPresenter>

    @ProvidePresenter
    fun providePresenter(): NewStudentTaskPresenter {
        return presenterProvider.get().apply {
            extractInitParams<NewTaskInitParams>().groupName?.let { init(it) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MaskedTextChangedListener.installOn(
            edt_deadline,
            "[00].[00].[0000]",
            null
        )

        edt_description_task_student.addTextChangedListener { presenter.onDescriptionChange(it.trim()) }
        btn_add_file_student.setOnClickListener { presenter.onFileChoose() }
        btn_send_task_student.setOnClickListener { presenter.onAdd() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_PICK_FILE) {
            data?.data?.let { uri ->
                val file = context?.contentResolver?.openInputStream(uri)?.readBytes()
                val fileName = uri.getFilename(context)
                val fileType = context?.contentResolver?.getType(uri)

                presenter.onFileChange(FileModel(file, fileName, fileType))
            }
        }
    }

    override fun showTaskGroup(name: String) {
        edt_group.setText(name)
    }

    override fun setSpinnerAdapter(items: List<String>) {
        val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.item_spinner, items)
        spinnerAdapter.setDropDownViewResource(R.layout.item_spinner_dropdown)

        spinner_discipline.adapter = spinnerAdapter
        spinner_discipline.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position in 0 until items.size) {
                    presenter.onDisciplineChange(items[position])
                } else {
                    presenter.onDisciplineChange(null)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                presenter.onDisciplineChange(null)
            }
        }
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun showRetry(errorText: String) {
        progress_error.visibility = View.VISIBLE

        text_retry.text = errorText
        btn_retry.setOnClickListener { presenter.onRetry() }
    }

    override fun hideRetry() {
        progress_error.visibility = View.GONE
    }

    override fun openFileChoose() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, REQUEST_CODE_PICK_FILE)
    }

    override fun setButtonEnabled(enabled: Boolean) {
        btn_add_task.isEnabled = enabled
    }

    override fun showChoosedFile(fileName: String) {
        container_picked_file.visibility = View.VISIBLE
        tv_picked_file_name.text = fileName
    }
}
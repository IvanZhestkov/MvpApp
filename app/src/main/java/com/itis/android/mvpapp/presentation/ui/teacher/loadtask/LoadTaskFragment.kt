package com.itis.android.mvpapp.presentation.ui.teacher.loadtask

import android.Manifest
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
import kotlinx.android.synthetic.main.fragment_load_task.*
import com.itis.android.mvpapp.presentation.utils.extensions.toast
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import android.app.Activity.RESULT_OK
import com.itis.android.mvpapp.presentation.utils.extensions.extractInitParams
import com.itis.android.mvpapp.presentation.utils.extensions.putInitParams
import com.itis.android.mvpapp.router.initparams.LoadTaskInitParams

class LoadTaskFragment : BaseFragment(), LoadTaskView, EasyPermissions.PermissionCallbacks {

    private var docPaths: ArrayList<String> = ArrayList()

    companion object {
        const val RC_FILE_PICKER_PERM = 321
        const val MAX_ATTACHMENT_COUNT = 10

        fun getInstance(initParams: LoadTaskInitParams): LoadTaskFragment {
            return LoadTaskFragment().also {
                it.putInitParams(initParams)
            }
        }
    }

    override val mainContentLayout = R.layout.fragment_load_task

    override val enableBackArrow = true

    override val toolbarTitle = R.string.toolbar_task

    override val menu: Int?
        get() = null

    @InjectPresenter
    lateinit var presenter: LoadTaskPresenter

    @Inject
    lateinit var presenterProvider: Provider<LoadTaskPresenter>

    @ProvidePresenter
    fun providePresenter(): LoadTaskPresenter {
        return presenterProvider.get().apply {
            extractInitParams<LoadTaskInitParams>().groupName?.let { init(it) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initActionView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        baseActivity.toast("Picked file: ???", Toast.LENGTH_LONG)

        if (requestCode == RC_FILE_PICKER_PERM && resultCode == RESULT_OK) {


        }
    }

    override fun showTaskGroup(name: String) {
        tv_group.text = name
    }

    private fun initActionView() {
        btn_load_task.setOnClickListener {
            pickDocClicked()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @AfterPermissionGranted(RC_FILE_PICKER_PERM)
    fun pickDocClicked() {
        if (EasyPermissions.hasPermissions(baseActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            onPickDoc()
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this, getString(R.string.rationale_doc_picker),
                    RC_FILE_PICKER_PERM, Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    }

    private fun onPickDoc() {
        if (docPaths.size == MAX_ATTACHMENT_COUNT) {
            baseActivity.toast("Cannot select more than $MAX_ATTACHMENT_COUNT items", Toast.LENGTH_SHORT)
        } else {

        }
    }
}
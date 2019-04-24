package com.itis.android.mvpapp.presentation.ui.main.profile

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.model.Discipline
import com.itis.android.mvpapp.model.User
import com.itis.android.mvpapp.presentation.adapter.DisciplineAdapter
import com.itis.android.mvpapp.presentation.base.BaseFragment
import com.itis.android.mvpapp.presentation.ui.auth.AuthActivity
import kotlinx.android.synthetic.main.fragment_teacher_profile.*
import javax.inject.Inject
import javax.inject.Provider

class ProfileFragment : BaseFragment(), ProfileView {

    companion object {
        fun getInstance() = ProfileFragment()
    }

    override val mainContentLayout: Int
        get() = R.layout.fragment_teacher_profile

    override val enableBackArrow: Boolean
        get() = false

    override val toolbarTitle: Int?
        get() = R.string.screen_name_profile

    override val menu: Int?
        get() = null

    @InjectPresenter
    lateinit var presenter: ProfilePresenter

    @Inject
    lateinit var presenterProvider: Provider<ProfilePresenter>

    @ProvidePresenter
    fun providePresenter(): ProfilePresenter {
        return presenterProvider.get()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        initActionViews()
    }

    override fun showProfile(user: User?) {
        //createList()
        tv_name.text = getString(R.string.test_name, user?.first_name, user?.last_name, user?.middle_name)
        tv_birthday.text = user?.birth_date
        tv_email.text = user?.email
        tv_phone.text = user?.phone
    }

    override fun showDisciplines(items: List<Discipline>) {
        (rv_disciplines.adapter as DisciplineAdapter).addItems(items)
    }

    private fun initList() {
        rv_disciplines.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = DisciplineAdapter()
            isNestedScrollingEnabled = false
        }
    }

    private fun initActionViews() {
        btn_logout.setOnClickListener {
            presenter.logout()
            val intent = Intent(baseActivity, AuthActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }

    private fun createList() {
        val disciplines = ArrayList<Discipline>()
        disciplines.add(Discipline("Информационная безопасность", listOf("11-501 - 11-508", "15-603 - 15-605", "8-305")))
        disciplines.add(Discipline("Управление проектами", listOf("11-501 - 11-508", "15-603 - 15-605", "8-305")))
        disciplines.add(Discipline("База данных", listOf("11-501 - 11-508", "15-603 - 15-605", "8-305")))

        (rv_disciplines.adapter as DisciplineAdapter).addItems(disciplines)
    }
}
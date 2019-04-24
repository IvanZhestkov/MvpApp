package com.itis.android.mvpapp.presentation.ui.main.profile

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.itis.android.mvpapp.model.Course
import com.itis.android.mvpapp.model.Discipline
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.router.MainRouter
import javax.inject.Inject
import com.itis.android.mvpapp.model.User

@InjectViewState
class ProfilePresenter
@Inject constructor() : BasePresenter<ProfileView>() {

    @Inject
    lateinit var profileRouter: MainRouter

    private val auth: FirebaseAuth? = FirebaseAuth.getInstance()

    private val user: FirebaseUser? = auth?.currentUser

    private var database = FirebaseDatabase.getInstance()

    private lateinit var myRef: DatabaseReference

    private val mapCourse: MutableMap<String?, MutableList<String?>> = HashMap()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Log.d("TAG userID: ", user?.uid)
        loadProfile()
        loadDisciplines()
    }

    private fun loadProfile() {
        Log.d("TAG loadProfile(): ", "start")

        myRef = database.getReference("users")

        user?.uid?.let { myRef.child(it) }
            ?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(User::class.java)
                    Log.d("TAG loadProfile(): ", user.toString())
                    viewState.showProfile(user)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("TAG loadProfile(): ", "error")
                }
            })
    }

    private fun loadDisciplines() {
        val listGroup: MutableList<String?> = ArrayList()
        val listCourse: MutableList<Course> = mutableListOf()
        val listDiscipline: MutableList<Discipline> = mutableListOf()

        myRef = database.getReference("courses")
        myRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.d("TAG loadDisciplines(): ", p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                listCourse.clear()
                listGroup.clear()
                listDiscipline.clear()

                p0.children.mapNotNullTo(listCourse) {
                    it.getValue<Course>(Course::class.java)
                }
                listCourse.filter { it.professor_id == user?.uid }.forEach{
                    listGroup.add(it.group_id)
                    mapCourse[it.subject_name] = listGroup
                }
                Log.d("TAG loadDisciplines(): ", "map courses: $mapCourse")

                mapCourse.toList().forEach {
                    listDiscipline.add(Discipline(it.first, it.second))
                }
                Log.d("TAG loadDisciplines(): ", "disciplines: $listDiscipline")
                viewState.showDisciplines(listDiscipline)
            }
        })
    }

    fun logout() {
        auth?.signOut()
    }
}
package com.itis.android.mvpapp.data.repository.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.itis.android.mvpapp.data.repository.TasksRepository
import com.itis.android.mvpapp.presentation.model.*
import io.reactivex.Single
import io.reactivex.subjects.AsyncSubject
import java.lang.Exception
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor() : TasksRepository {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var firebaseDB: FirebaseDatabase

    private var firebaseUser: FirebaseUser? = null

    private fun getTasksByCoursesId(list: List<String>): Single<List<Task>> {
        val ref = firebaseDB.getReference("tasks")

        val subject = AsyncSubject.create<Pair<String, List<Task>>>()

        val tasks: MutableList<Task> = mutableListOf()

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                subject.onNext(Pair(error.message, emptyList()))
                subject.onComplete()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    if (list.contains(it.key)) {
                        it.children.mapNotNullTo(tasks) {
                            it.getValue<Task>(Task::class.java)
                        }
                    }
                }
                subject.onNext(Pair("", tasks))
                subject.onComplete()
            }
        })

        return subject.singleOrError().flatMap { (errorMessage, tasks) ->
            when {
                errorMessage.isEmpty() -> Single.just(tasks)
                else -> Single.error(Exception())
            }
        }
    }

    override fun getTasks(groupId: String): Single<List<Task>> {
        val ref = firebaseDB.getReference("courses")

        val listCourseId: MutableList<String> = mutableListOf()

        val subject = AsyncSubject.create<Pair<String, Single<List<Task>>>>()

        ref.orderByChild("group_id").equalTo(groupId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        subject.onNext(Pair(error.message, Single.just(emptyList())))
                        subject.onComplete()
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.mapNotNullTo(listCourseId) {
                            it.key
                        }

                        subject.onNext(Pair("", getTasksByCoursesId(listCourseId)))
                        subject.onComplete()
                    }
                })

        return subject.singleOrError().flatMap { (errorMessage, tasks) ->
            when {
                errorMessage.isEmpty() -> tasks
                else -> Single.error(Exception())
            }
        }
    }
}
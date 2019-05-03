package com.itis.android.mvpapp.data.repository.impl

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.itis.android.mvpapp.data.network.isOnline
import com.itis.android.mvpapp.data.pojo.TaskSolutionItem
import com.itis.android.mvpapp.data.repository.*
import com.itis.android.mvpapp.presentation.model.UserSolutionModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.AsyncSubject
import java.lang.Exception
import javax.inject.Inject

class TaskSolutionRepositoryImpl @Inject constructor() : TaskSolutionRepository {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var firebaseDB: FirebaseDatabase

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var studentsRepository: StudentsRepository

    override fun getTaskSolutions(
            disciplineId: String?,
            taskId: String?,
            groupId: String?
    ): Single<List<UserSolutionModel>> {
        val ref = disciplineId?.let { firebaseDB.getReference("solutions").child(it) }

        val subject = AsyncSubject.create<Pair<String, List<UserSolutionModel>>>()
        val solutions: MutableList<TaskSolutionItem> = mutableListOf()

        ref?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                subject.onNext(Pair(error.message, emptyList()))
                subject.onComplete()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { userSnapshot ->
                    userSnapshot.children.filter { it.key == taskId }.forEach { taskSnapshot ->
                        taskSnapshot.children.mapNotNullTo(solutions) { solutionSnapshot ->
                            solutionSnapshot.getValue<TaskSolutionItem>(TaskSolutionItem::class.java).also {
                                it?.id = solutionSnapshot.key
                                it?.taskId = taskSnapshot.key
                                it?.disciplineId = snapshot.key
                                it?.userId = userSnapshot.key
                            }
                        }
                    }
                }

                var d = groupId?.let {
                    studentsRepository
                            .getStudentsByGroupId(it)
                            .toObservable()
                            .flatMap { Observable.fromIterable(it) }
                            .flatMap { studentId ->
                                userRepository
                                        .getUserById(studentId)
                                        .toObservable()
                                        .map { user ->
                                            UserSolutionModel(user,
                                                    solutions.filter { it.userId == studentId }.getOrNull(0)
                                            )
                                        }
                            }.toList()
                            .subscribe({
                                subject.onNext(Pair("", it))
                                subject.onComplete()
                            }, {
                                subject.onNext(Pair(it.message ?: "error", emptyList()))
                                subject.onComplete()
                            })
                }
            }
        })

        return Single.just(isOnline(context)).flatMap { isConnected ->
            if (isConnected) {
                subject.singleOrError().flatMap { (errorMessage, solutions) ->
                    when {
                        errorMessage.isEmpty() -> Single.just(solutions)
                        else -> Single.error(Exception())
                    }
                }
            } else {
                Single.error(Exception())
            }
        }
    }

    override fun updateSolutionStatus(solution: TaskSolutionItem, status: String) {
        val disciplineId = solution.disciplineId ?: ""
        val userId = solution.userId ?: ""
        val taskId = solution.taskId ?: ""
        val solutionId = solution.id ?: ""

        val ref = firebaseDB.getReference("solutions")
        ref.child(disciplineId).child(userId).child(taskId).child(solutionId).child("status").setValue(status)
    }
}
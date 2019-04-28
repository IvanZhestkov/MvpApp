package com.itis.android.mvpapp.data.repository.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.itis.android.mvpapp.data.pojo.TaskItem
import com.itis.android.mvpapp.data.pojo.TaskSolutionItem
import com.itis.android.mvpapp.data.pojo.TeacherDisciplineItem
import com.itis.android.mvpapp.data.repository.*
import com.itis.android.mvpapp.presentation.model.TaskModel
import com.itis.android.mvpapp.presentation.model.TaskModelMapper
import com.itis.android.mvpapp.presentation.model.TaskSolutionModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.AsyncSubject
import java.lang.Exception
import javax.inject.Inject

class TaskSolutionRepositoryImpl @Inject constructor() : TaskSolutionRepository {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var firebaseDB: FirebaseDatabase

    @Inject
    lateinit var userRepository: UserRepository

    override fun getTaskSolutions(disciplineId: String?, taskId: String?): Single<List<TaskSolutionModel>> {
        val ref = disciplineId?.let { firebaseDB.getReference("solutions").child(it) }

        val subject = AsyncSubject.create<Pair<String, List<TaskSolutionModel>>>()
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
                                it?.taskId = taskSnapshot.key
                                it?.disciplineId = snapshot.key
                                it?.userId = userSnapshot.key
                            }
                        }
                    }
                }

                val d = Observable
                        .fromIterable(solutions)
                        .flatMap { solution ->
                            userRepository
                                    .getUserById(solution.userId ?: "")
                                    .toObservable()
                                    .map { TaskSolutionModel(it, solutions.filter { it.userId == solution.userId }) }
                        }.toList()
                        .subscribe({
                            subject.onNext(Pair("", it))
                            subject.onComplete()
                        }, {
                            subject.onNext(Pair(it.message ?: "error", emptyList()))
                            subject.onComplete()
                        })
            }
        })

        return subject.singleOrError().flatMap { (errorMessage, solutions) ->
            when {
                errorMessage.isEmpty() -> Single.just(solutions)
                else -> Single.error(Exception())
            }
        }
    }
}
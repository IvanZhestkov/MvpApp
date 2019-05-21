package com.itis.android.mvpapp.data.repository.impl

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.itis.android.mvpapp.data.network.isOnline
import com.itis.android.mvpapp.data.network.pojo.firebase.request.UploadTaskSolutionItem
import com.itis.android.mvpapp.data.pojo.TaskSolutionItem
import com.itis.android.mvpapp.data.repository.*
import com.itis.android.mvpapp.presentation.model.UploadTaskSolutionModel
import com.itis.android.mvpapp.presentation.model.UserSolutionModel
import com.itis.android.mvpapp.presentation.util.extensions.getFileName
import io.reactivex.Completable
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
    lateinit var firebaseStorage: FirebaseStorage

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
                snapshot.children.filter { it.key == taskId }.forEach { taskSnapshot ->
                    taskSnapshot.children.forEach { userSnapshot ->
                        userSnapshot.children.mapNotNullTo(solutions) { solutionSnapshot ->
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

    override fun getUserTaskSolution(disciplineId: String?, taskId: String?): Single<List<TaskSolutionItem>> {
        val userId = firebaseAuth.currentUser?.uid.orEmpty()
        val solutions: MutableList<TaskSolutionItem> = mutableListOf()

        val ref = firebaseDB.getReference("solutions")
                .child(disciplineId.orEmpty())
                .child(taskId.orEmpty())
                .child(userId)

        val subject = AsyncSubject.create<Pair<String, List<TaskSolutionItem>>>()

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                subject.onNext(Pair(error.message, emptyList()))
                subject.onComplete()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.mapNotNullTo(solutions) { solutionSnapshot ->
                    solutionSnapshot.getValue<TaskSolutionItem>(TaskSolutionItem::class.java).also {
                        it?.id = snapshot.key
                        it?.taskId = taskId
                        it?.disciplineId = disciplineId
                        it?.userId = userId
                    }
                }

                subject.onNext(Pair("", solutions))
                subject.onComplete()
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

    override fun addTaskSolution(solution: UploadTaskSolutionModel): Completable {
        val addTaskSubject = AsyncSubject.create<Boolean>()
        val addFileSubject = AsyncSubject.create<Boolean>()

        val filename = getFileName()
        solution.file?.let {
            val ref = firebaseStorage.reference
            ref.child(filename).putBytes(it).addOnCompleteListener {
                addFileSubject.onNext(it.isSuccessful)
                addFileSubject.onComplete()
            }.addOnCanceledListener {
                addFileSubject.onNext(false)
                addFileSubject.onComplete()
            }
        }

        val ref = firebaseDB.getReference("solutions")
                .child(solution.disciplineId.orEmpty())
                .child(solution.taskId.orEmpty())
                .child(firebaseAuth.currentUser?.uid.orEmpty())
                .push()

        val uploadTaskSolutionModel = UploadTaskSolutionItem(
                "",
                "",
                0,
                filename,
                "pending",
                (System.currentTimeMillis() / 1000)
        )

        ref.setValue(uploadTaskSolutionModel).addOnCompleteListener {
            addTaskSubject.onNext(true)
            addTaskSubject.onComplete()
        }.addOnFailureListener {
            addTaskSubject.onNext(false)
            addTaskSubject.onComplete()
        }

        return addFileSubject.flatMapCompletable { addFileSuccess ->
            if (addFileSuccess) {
                addTaskSubject.flatMapCompletable { addTaskSuccess ->
                    if (addTaskSuccess) {
                        Completable.complete()
                    } else {
                        Completable.error(Exception())
                    }
                }
            } else {
                Completable.error(Exception())
            }
        }
    }

    override fun updateSolutionStatus(solution: TaskSolutionItem) {
        firebaseDB.getReference("solutions")
                .child(solution.disciplineId.toString())
                .child(solution.taskId.toString())
                .child(solution.userId.toString())
                .child(solution.id.toString())
                .child("status")
                .setValue(solution.status)
    }

    override fun addSolutionComment(solution: TaskSolutionItem) {
        firebaseDB.getReference("solutions")
                .child(solution.disciplineId.toString())
                .child(solution.taskId.toString())
                .child(solution.userId.toString())
                .child(solution.id.toString())
                .child("commentary")
                .setValue(solution.commentary)
    }
}

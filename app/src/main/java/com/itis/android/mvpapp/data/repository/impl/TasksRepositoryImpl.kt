package com.itis.android.mvpapp.data.repository.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.itis.android.mvpapp.data.network.pojo.firebase.request.UploadTaskItem
import com.itis.android.mvpapp.data.network.pojo.firebase.response.TaskItem
import com.itis.android.mvpapp.data.network.pojo.studapi.request.UploadTaskRequest
import com.itis.android.mvpapp.data.network.request.ApiRequest
import com.itis.android.mvpapp.data.repository.DisciplinesRepository
import com.itis.android.mvpapp.data.repository.TasksRepository
import com.itis.android.mvpapp.presentation.model.FileModel
import com.itis.android.mvpapp.presentation.model.TaskModel
import com.itis.android.mvpapp.presentation.model.TaskModelMapper
import com.itis.android.mvpapp.presentation.model.UploadTaskModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.AsyncSubject
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import java.lang.Exception
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor() : TasksRepository {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var firebaseDB: FirebaseDatabase

    @Inject
    lateinit var disciplinesRepository: DisciplinesRepository

    @Inject
    lateinit var apiRequest: ApiRequest

    override fun getTasks(): Single<List<TaskModel>> {
        val ref = firebaseDB.getReference("tasks")

        val subject = AsyncSubject.create<Pair<String, List<TaskModel>>>()
        val tasks: MutableList<TaskItem> = mutableListOf()

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                subject.onNext(Pair(error.message, emptyList()))
                subject.onComplete()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { disciplineSnapShot ->
                    disciplineSnapShot.children.mapNotNullTo(tasks) { taskSnapShot ->
                        taskSnapShot.getValue<TaskItem>(TaskItem::class.java).also {
                            it?.taskId = taskSnapShot.key
                            it?.disciplineId = disciplineSnapShot.key
                        }
                    }
                }

                val d = Observable
                        .fromIterable(tasks)
                        .flatMap { task ->
                            disciplinesRepository
                                    .getDisciplineById(task.disciplineId ?: "")
                                    .toObservable()
                                    .map { TaskModelMapper.map(task, it) }
                                    .filter { it.professorId == firebaseAuth.currentUser?.uid }
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

        return subject.singleOrError().flatMap { (errorMessage, tasks) ->
            when {
                errorMessage.isEmpty() -> Single.just(tasks)
                else -> Single.error(Exception())
            }
        }
    }

    override fun addTask(task: UploadTaskModel): Completable {
        val subject = AsyncSubject.create<Boolean>()

        val d = disciplinesRepository
                .getDisciplineByNameAndGroup(task.discipline.orEmpty(), task.group.orEmpty())
                .subscribe({ discipline ->
                    val ref = firebaseDB.getReference("tasks").child(discipline.id.orEmpty()).push()

                    val uploadTaskItem = UploadTaskItem(
                            task.name,
                            task.description,
                            task.deadLine,
                            System.currentTimeMillis().toString()
                    )

                    ref.setValue(uploadTaskItem).addOnCompleteListener {
                        val taskRequest = UploadTaskRequest(discipline.id, ref.key, firebaseAuth.currentUser?.uid)

                        sendFile(taskRequest, task.file).subscribe({
                            subject.onNext(it.isSuccessful)
                            subject.onComplete()
                        }, {
                            subject.onNext(false)
                            subject.onComplete()
                        })
                    }

                }, {
                    subject.onNext(false)
                    subject.onComplete()
                })

        return subject.flatMapCompletable { success ->
            if (success) {
                Completable.complete()
            } else {
                Completable.error(Exception())
            }
        }
    }


    private fun sendFile(uploadTaskRequest: UploadTaskRequest, fileModel: FileModel?): Completable {
        fileModel?.fileType?.let { fileType ->
            fileModel.file?.let { file ->
                val fBody = RequestBody.create(MediaType.parse(fileModel.fileType.orEmpty()), file)
                val multipartFile = MultipartBody.Part.createFormData("file", fileModel.fileName, fBody)

                val courseId = RequestBody.create(MediaType.parse("text/plain"), uploadTaskRequest.courseID.orEmpty())
                val taskId = RequestBody.create(MediaType.parse("text/plain"), uploadTaskRequest.taskID.orEmpty())
                val idToken = RequestBody.create(MediaType.parse("text/plain"), uploadTaskRequest.idToken.orEmpty())

                return apiRequest.uploadTaskCompletable(
                        multipartFile,
                        courseId,
                        taskId,
                        idToken
                )
            }
        }

        return Completable.error(Exception())
    }
}
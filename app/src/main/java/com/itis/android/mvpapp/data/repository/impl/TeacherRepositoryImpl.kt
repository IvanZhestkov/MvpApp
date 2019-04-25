package com.itis.android.mvpapp.data.repository.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.itis.android.mvpapp.data.repository.TeacherRepository
import com.itis.android.mvpapp.presentation.model.Course
import com.itis.android.mvpapp.data.pojo.TeacherDisciplineItem
import com.itis.android.mvpapp.data.pojo.TeacherInfoItem
import com.itis.android.mvpapp.data.repository.ConnectionRepository
import com.itis.android.mvpapp.presentation.model.TeacherInfoModel
import com.itis.android.mvpapp.presentation.model.TeacherInfoModelMapper
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.AsyncSubject
import java.lang.IllegalArgumentException
import javax.inject.Inject
import kotlin.Exception


class TeacherRepositoryImpl @Inject constructor() : TeacherRepository {

    @Inject
    lateinit var firebaseDB: FirebaseDatabase

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var connectionRepository: ConnectionRepository

    private var firebaseUser: FirebaseUser? = null

    override fun getTeacherInfoObservable(): Observable<TeacherInfoModel> {
        return Single.zip(
            getTeacherInfo(),
            getDisciplineSingle(),
            BiFunction<TeacherInfoItem, List<TeacherDisciplineItem>, TeacherInfoModel>
            { t1, t2 ->
                TeacherInfoModelMapper.map(t1, t2)
            }).toObservable()
    }

    private fun getTeacherInfo(): Single<TeacherInfoItem> {
        firebaseUser = firebaseAuth.currentUser
        val ref = firebaseDB.getReference("users").also { it.keepSynced(true) }

        val subject = AsyncSubject.create<Pair<String, TeacherInfoItem>>()

        firebaseUser?.uid.let { ref.child(it.toString()) }
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(TeacherInfoItem::class.java)
                    subject.onNext(
                        Pair(
                            "", user
                                ?: throw IllegalArgumentException("firebase user is null")
                        )
                    )
                    subject.onComplete()
                }

                override fun onCancelled(error: DatabaseError) {
                    subject.onNext(Pair(error.message, TeacherInfoItem()))
                    subject.onComplete()
                }
            })

        return connectionRepository.getCheckConnection().flatMapSingle { isConnected ->
            if (isConnected) {
                subject.singleOrError().flatMap { (errorMessage, teacherInfo) ->
                    when {
                        errorMessage.isEmpty() -> Single.just(teacherInfo)
                        else -> Single.error(Exception())
                    }
                }
            } else {
                Single.error(Exception())
            }
        }.singleOrError()
    }

    private fun getDisciplineSingle(): Single<List<TeacherDisciplineItem>> {
        val ref = firebaseDB.getReference("courses")

        val mapCourse: MutableMap<String?, MutableList<String?>> = HashMap()

        val subject = AsyncSubject.create<Pair<String, List<TeacherDisciplineItem>>>()

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                subject.onNext(Pair(error.message, emptyList()))
                subject.onComplete()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val listCourse: MutableList<Course> = mutableListOf()

                snapshot.children.mapNotNullTo(listCourse) {
                    it.getValue<Course>(Course::class.java)
                }
                listCourse.filter { it.professor_id == firebaseUser?.uid }.forEach { course ->
                    mapCourse[course.subject_name] =
                        (mapCourse[course.subject_name]
                            ?: mutableListOf()).also { it.add(course.group_id) }
                }

                val disciplines = mapCourse.toList().map { TeacherDisciplineItem(it.first, it.second) }

                subject.onNext(Pair("", disciplines))
                subject.onComplete()
            }
        })

        return connectionRepository.getCheckConnection().flatMapSingle { connected ->
            if (connected) {
                subject.singleOrError().flatMap { (errorMessage, disciplines) ->
                    when {
                        errorMessage.isEmpty() -> Single.just(disciplines)
                        else -> Single.error(Exception())
                    }
                }
            } else {
                Single.error(Exception())
            }
        }.singleOrError()
    }
}
package com.itis.android.mvpapp.data.repository.impl

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.itis.android.mvpapp.data.network.isOnline
import com.itis.android.mvpapp.data.network.pojo.firebase.response.TeacherInfoItem
import com.itis.android.mvpapp.data.repository.DisciplinesRepository
import com.itis.android.mvpapp.data.repository.GroupsRepository
import com.itis.android.mvpapp.data.repository.StudentRepository
import com.itis.android.mvpapp.presentation.model.GroupModel
import com.itis.android.mvpapp.presentation.model.StudentInfoItem
import com.itis.android.mvpapp.presentation.model.StudentInfoModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.subjects.AsyncSubject
import java.lang.IllegalArgumentException
import javax.inject.Inject

class StudentRepositoryImpl @Inject constructor() : StudentRepository {

    @Inject
    lateinit var firebaseDB: FirebaseDatabase

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var groupsRepository: GroupsRepository

    override fun getStudentInfoObservable(): Observable<StudentInfoModel> {
        return Single.zip(
            getTeacherInfo(),
            getGroupByUID(),
            getAverageScoreByUID(),
            Function3<TeacherInfoItem, String, Long, StudentInfoModel>
            { t1, t2,t3 ->
                StudentInfoModel(
                    id = t1.id,
                    email = t1.email,
                    firstName = t1.first_name,
                    lastName = t1.last_name,
                    middleName = t1.middle_name,
                    birthDate = t1.birth_date,
                    phone = t1.phone,
                    photo = t1.photo,
                    role = t1.role,
                    averageScore = t3.toString(),
                    groupId = t2
                )
            }).toObservable()
    }

    override fun getAverageScoreByUID(): Single<Long> {
        val ref = firebaseDB.getReference("students").also { it.keepSynced(true) }

        val subject = AsyncSubject.create<Pair<String, StudentInfoItem>>()

        firebaseAuth.currentUser?.uid.let { ref.child(it.toString()) }
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(StudentInfoItem::class.java)
                    subject.onNext(
                        Pair(
                            "", user
                                ?: throw IllegalArgumentException("firebase user is null")
                        )
                    )
                    subject.onComplete()
                }

                override fun onCancelled(error: DatabaseError) {
                    subject.onNext(Pair(error.message, StudentInfoItem()))
                    subject.onComplete()
                }
            })

        return Single.just(isOnline(context)).flatMap { isConnected ->
            if (isConnected) {
                subject.singleOrError().flatMap { (errorMessage, studentInfo) ->
                    when {
                        errorMessage.isEmpty() -> Single.just(studentInfo.average_score)
                        else -> Single.error(Exception())
                    }
                }
            } else {
                Single.error(Exception())
            }
        }
    }

    override fun getGroupByUID(): Single<String> {
        val ref = firebaseDB.getReference("students").also { it.keepSynced(true) }

        val subject = AsyncSubject.create<Pair<String, StudentInfoItem>>()

        firebaseAuth.currentUser?.uid.let { ref.child(it.toString()) }
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(StudentInfoItem::class.java)
                    subject.onNext(
                        Pair(
                            "", user
                                ?: throw IllegalArgumentException("firebase user is null")
                        )
                    )
                    subject.onComplete()
                }

                override fun onCancelled(error: DatabaseError) {
                    subject.onNext(Pair(error.message, StudentInfoItem()))
                    subject.onComplete()
                }
            })

        return Single.just(isOnline(context)).flatMap { isConnected ->
            if (isConnected) {
                subject.singleOrError().flatMap { (errorMessage, studentInfo) ->
                    when {
                        errorMessage.isEmpty() -> Single.just(studentInfo.group_id)
                        else -> Single.error(Exception())
                    }
                }
            } else {
                Single.error(Exception())
            }
        }
    }

    private fun getTeacherInfo(): Single<TeacherInfoItem> {
        val ref = firebaseDB.getReference("users").also { it.keepSynced(true) }

        val subject = AsyncSubject.create<Pair<String, TeacherInfoItem>>()

        firebaseAuth.currentUser?.uid.let { ref.child(it.toString()) }
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

        return Single.just(isOnline(context)).flatMap { isConnected ->
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
        }
    }
}
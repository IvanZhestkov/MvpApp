package com.itis.android.mvpapp.data.repository.impl

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.itis.android.mvpapp.data.network.isOnline
import com.itis.android.mvpapp.data.pojo.TeacherDisciplineItem
import com.itis.android.mvpapp.data.pojo.TeacherInfoItem
import com.itis.android.mvpapp.data.repository.DisciplinesRepository
import com.itis.android.mvpapp.data.repository.GroupsRepository
import com.itis.android.mvpapp.data.repository.StudentRepository
import com.itis.android.mvpapp.presentation.model.GroupModel
import com.itis.android.mvpapp.presentation.model.StudentInfoModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.AsyncSubject
import java.lang.IllegalArgumentException
import javax.inject.Inject

class StudentRepositoryImpl : StudentRepository {

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
            groupsRepository.getGroupsSingle(),
            BiFunction<TeacherInfoItem, List<GroupModel>, StudentInfoModel>
            { t1, t2 ->
                StudentInfoModel(id = t1.id,
                    email = t1.email,
                    firstName = t1.first_name,
                    lastName = t1.last_name,
                    middleName = t1.middle_name,
                    birthDate = t1.birth_date,
                    phone = t1.phone,
                    photo = t1.photo,
                    role = t1.role,
                    averageScore = (Math.random() * 100).toInt(),
                    groupId = 11-605)
            }).toObservable()
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
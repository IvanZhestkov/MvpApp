package com.itis.android.mvpapp.data.repository.impl

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.itis.android.mvpapp.data.network.isOnline
import com.itis.android.mvpapp.data.repository.TeacherRepository
import com.itis.android.mvpapp.data.network.pojo.firebase.response.TeacherDisciplineItem
import com.itis.android.mvpapp.data.network.pojo.firebase.response.TeacherInfoItem
import com.itis.android.mvpapp.data.repository.DisciplinesRepository
import com.itis.android.mvpapp.presentation.model.TeacherInfoModel
import com.itis.android.mvpapp.presentation.model.TeacherInfoModelMapper
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
    lateinit var context: Context

    @Inject
    lateinit var disciplinesRepository: DisciplinesRepository

    override fun getTeacherInfoObservable(): Observable<TeacherInfoModel> {
        return Single.zip(
            getTeacherInfo(),
            disciplinesRepository.getDisciplinesSingle(),
            BiFunction<TeacherInfoItem, List<TeacherDisciplineItem>, TeacherInfoModel>
            { t1, t2 ->
                TeacherInfoModelMapper.map(t1, t2)
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
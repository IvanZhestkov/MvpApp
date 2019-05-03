package com.itis.android.mvpapp.data.repository.impl

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.itis.android.mvpapp.data.network.isOnline
import com.itis.android.mvpapp.data.network.pojo.firebase.response.TeacherDisciplineItem
import com.itis.android.mvpapp.data.repository.DisciplinesRepository
import io.reactivex.Single
import io.reactivex.subjects.AsyncSubject
import javax.inject.Inject

class DisciplinesRepositoryImpl @Inject constructor() : DisciplinesRepository {

    @Inject
    lateinit var firebaseDB: FirebaseDatabase

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var context: Context

    override fun getDisciplinesSingle(): Single<List<TeacherDisciplineItem>> {
        val ref = firebaseDB.getReference("courses")

        val subject = AsyncSubject.create<Pair<String, List<TeacherDisciplineItem>>>()

        ref.orderByChild("professor_id").equalTo(firebaseAuth.currentUser?.uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        subject.onNext(Pair(error.message, emptyList()))
                        subject.onComplete()
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val disciplines: MutableList<TeacherDisciplineItem> = mutableListOf()

                        snapshot.children.mapNotNullTo(disciplines) { data ->
                            data.getValue<TeacherDisciplineItem>(TeacherDisciplineItem::class.java).also { it?.id = data.key }
                        }

                        subject.onNext(Pair("", disciplines))
                        subject.onComplete()
                    }
                })

        return Single.just(isOnline(context)).flatMap { connected ->
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
        }
    }

    override fun getDisciplineById(id: String): Single<TeacherDisciplineItem> {
        val ref = firebaseDB.getReference("courses")

        val subject = AsyncSubject.create<Pair<String, TeacherDisciplineItem>>()

        ref.child(id).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val item = snapshot.getValue<TeacherDisciplineItem>(TeacherDisciplineItem::class.java).also { it?.id = snapshot.key }
                subject.onNext(Pair("", item ?: TeacherDisciplineItem()))
                subject.onComplete()
            }

            override fun onCancelled(error: DatabaseError) {
                subject.onNext(Pair(error.message, TeacherDisciplineItem()))
                subject.onComplete()
            }
        })

        return subject.singleOrError().flatMap { (errorMessage, discipline) ->
            when {
                errorMessage.isEmpty() -> Single.just(discipline)
                else -> Single.error(Exception())
            }
        }
    }
}
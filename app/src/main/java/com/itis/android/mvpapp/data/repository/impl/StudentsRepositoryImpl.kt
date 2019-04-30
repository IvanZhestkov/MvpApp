package com.itis.android.mvpapp.data.repository.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.itis.android.mvpapp.data.repository.StudentsRepository
import io.reactivex.Single
import io.reactivex.subjects.AsyncSubject
import java.lang.Exception
import javax.inject.Inject

class StudentsRepositoryImpl @Inject constructor() : StudentsRepository {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var firebaseDB: FirebaseDatabase

    override fun getStudentsByGroupId(id: String): Single<List<String>> {
        val ref = firebaseDB.getReference("students")

        val subject = AsyncSubject.create<Pair<String, List<String>>>()
        val studentsId: MutableList<String> = mutableListOf()

        ref.orderByChild("group_id").equalTo(id)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        subject.onNext(Pair(error.message, emptyList()))
                        subject.onComplete()
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.mapNotNullTo(studentsId) {
                            it.key
                        }
                        subject.onNext(Pair("", studentsId))
                        subject.onComplete()
                    }
                })

        return subject.singleOrError().flatMap { (errorMessage, studentsId) ->
            when {
                errorMessage.isEmpty() -> Single.just(studentsId)
                else -> Single.error(Exception())
            }
        }
    }
}
package com.itis.android.mvpapp.data.repository.impl

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.itis.android.mvpapp.data.repository.ConnectionRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.AsyncSubject
import java.lang.Exception
import javax.inject.Inject

class ConnectionRepositoryImpl @Inject constructor() : ConnectionRepository {

    @Inject
    lateinit var firebaseDatabase: FirebaseDatabase

    override fun getCheckConnection(): Observable<Boolean> {

        val subject = AsyncSubject.create<Boolean>()

        val connectedRef = firebaseDatabase.getReference(".info/connected")
        connectedRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val connected = snapshot.getValue(Boolean::class.java) ?: false
                subject.onNext(connected)
                subject.onComplete()
            }

            override fun onCancelled(error: DatabaseError) {
                subject.onNext(false)
                subject.onComplete()
            }
        })

        return subject
    }
}
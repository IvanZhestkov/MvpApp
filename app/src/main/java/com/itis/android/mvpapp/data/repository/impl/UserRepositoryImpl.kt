package com.itis.android.mvpapp.data.repository.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.itis.android.mvpapp.data.repository.LoginRepository
import com.itis.android.mvpapp.data.repository.UserRepository
import com.itis.android.mvpapp.presentation.model.*
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.subjects.AsyncSubject
import java.lang.Exception
import java.lang.IllegalArgumentException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var firebaseDB: FirebaseDatabase

    private var firebaseUser: FirebaseUser? = null

    override fun getUser(): Single<User> {
        firebaseUser = firebaseAuth.currentUser
        val ref = firebaseDB.getReference("users")

        val subject = AsyncSubject.create<Pair<String, User>>()

        firebaseUser?.uid.let { ref.child(it.toString()) }
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val user = dataSnapshot.getValue(User::class.java)
                        subject.onNext(Pair("", user
                                ?: throw IllegalArgumentException("firebase user is null")))
                        subject.onComplete()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        subject.onNext(Pair(error.message, User()))
                        subject.onComplete()
                    }
                })

        return subject.singleOrError().flatMap { (errorMessage, user) ->
            when {
                errorMessage.isEmpty() -> Single.just(user)
                else -> Single.error(Exception())
            }
        }
    }

    override fun getUserById(id: String): Single<User> {
        firebaseUser = firebaseAuth.currentUser
        val ref = firebaseDB.getReference("users").child(id)

        val subject = AsyncSubject.create<Pair<String, User>>()

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                subject.onNext(Pair("", user
                        ?: throw IllegalArgumentException("firebase user is null")))
                subject.onComplete()
            }

            override fun onCancelled(error: DatabaseError) {
                subject.onNext(Pair(error.message, User()))
                subject.onComplete()
            }
        })

        return subject.singleOrError().flatMap { (errorMessage, user) ->
            when {
                errorMessage.isEmpty() -> Single.just(user)
                else -> Single.error(Exception())
            }
        }
    }
}
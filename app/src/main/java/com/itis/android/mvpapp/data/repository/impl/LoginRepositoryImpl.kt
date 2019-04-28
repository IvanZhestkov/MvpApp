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

class LoginRepositoryImpl @Inject constructor() : LoginRepository {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var firebaseDB: FirebaseDatabase

    @Inject
    lateinit var userRepository: UserRepository

    override fun login(email: String, password: String): Single<User> {
        val subject = AsyncSubject.create<Pair<Boolean, Single<User>>>()

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        subject.onNext(Pair(it.isSuccessful, userRepository.getUser()))
                        subject.onComplete()
                    } else {
                        subject.onNext(Pair(it.isSuccessful, Single.just(User())))
                        subject.onComplete()
                    }
                }

        return subject.singleOrError().flatMap { (isSuccessful, user) ->
            when {
                isSuccessful -> user
                else -> Single.error(Exception())
            }
        }
    }
}
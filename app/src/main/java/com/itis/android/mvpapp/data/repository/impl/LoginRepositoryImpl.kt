package com.itis.android.mvpapp.data.repository.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.itis.android.mvpapp.data.network.pojo.firebase.response.UserItem
import com.itis.android.mvpapp.data.repository.LoginRepository
import com.itis.android.mvpapp.data.repository.UserRepository
import io.reactivex.Single
import io.reactivex.subjects.AsyncSubject
import java.lang.Exception
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor() : LoginRepository {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var firebaseDB: FirebaseDatabase

    @Inject
    lateinit var userRepository: UserRepository

    override fun login(email: String, password: String): Single<UserItem> {
        val subject = AsyncSubject.create<Pair<Boolean, Single<UserItem>>>()

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        subject.onNext(Pair(it.isSuccessful, userRepository.getUser()))
                        subject.onComplete()
                    } else {
                        subject.onNext(Pair(it.isSuccessful, Single.just(UserItem())))
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
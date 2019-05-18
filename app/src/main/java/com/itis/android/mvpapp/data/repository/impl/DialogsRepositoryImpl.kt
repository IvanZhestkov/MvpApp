package com.itis.android.mvpapp.data.repository.impl

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.itis.android.mvpapp.data.network.isOnline
import com.itis.android.mvpapp.data.network.pojo.firebase.response.CreateDialogItem
import com.itis.android.mvpapp.data.network.pojo.firebase.response.UserItem
import com.itis.android.mvpapp.data.repository.DialogsRepository
import com.itis.android.mvpapp.data.repository.MessagesRepository
import com.itis.android.mvpapp.data.repository.UserRepository
import com.itis.android.mvpapp.data.util.CredentialStorage
import com.itis.android.mvpapp.presentation.model.CreateDialogModel
import com.itis.android.mvpapp.presentation.model.DialogModel
import com.itis.android.mvpapp.presentation.model.DialogModelMapper
import com.itis.android.mvpapp.presentation.model.MessageModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.AsyncSubject
import java.lang.Exception
import javax.inject.Inject

class DialogsRepositoryImpl @Inject constructor() : DialogsRepository {

    @Inject
    lateinit var firebaseDB: FirebaseDatabase

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var messagesRepository: MessagesRepository

    @Inject
    lateinit var credentialStorage: CredentialStorage

    @Inject
    lateinit var usersRepository: UserRepository

    @Inject
    lateinit var context: Context

    override fun createDialog(studentId: String): Observable<String> {
        val asyncSubject = AsyncSubject.create<Pair<Boolean, String>>()

        val d = checkIfExistsChat(studentId, firebaseAuth.currentUser?.uid.orEmpty())
                .subscribe({ chatId ->
                    if (chatId.isEmpty()) {
                        val ref = firebaseDB.getReference("chats").push()
                        ref.setValue(CreateDialogItem(firebaseAuth.uid, studentId))
                                .addOnCompleteListener {
                                    asyncSubject.onNext(Pair(true, ref.key.orEmpty()))
                                    asyncSubject.onComplete()
                                }.addOnCanceledListener {
                                    asyncSubject.onNext(Pair(false, ""))
                                    asyncSubject.onComplete()
                                }
                    } else {
                        asyncSubject.onNext(Pair(true, chatId))
                        asyncSubject.onComplete()
                    }
                }, {
                    asyncSubject.onNext(Pair(false, ""))
                    asyncSubject.onComplete()
                })


        return asyncSubject.flatMap { (success, chatId) ->
            if (success) {
                Observable.just(chatId)
            } else {
                Observable.error(Exception())
            }
        }
    }

    private fun checkIfExistsChat(studentId: String, teacherId: String): Observable<String> {
        val asyncSubject = AsyncSubject.create<Pair<Boolean, String>>()
        val child = when (credentialStorage.getUserRole().orEmpty()) {
            "PROFESSOR" -> "professor_id"
            else -> "student_id"
        }
        firebaseDB
                .getReference("chats")
                .orderByChild(child)
                .equalTo(teacherId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val items = mutableListOf<String>()

                        snapshot.children.forEach { ds ->
                            val item = ds.getValue(CreateDialogItem::class.java)
                            when (child) {
                                "professor_id" -> {
                                    if (item?.student_id == studentId) {
                                        items.add(ds.key.orEmpty())
                                    }
                                }
                                else -> {
                                    if (item?.professor_id == studentId) {
                                        items.add(ds.key.orEmpty())
                                    }
                                }
                            }

                        }
                        if (items.size == 0) {
                            asyncSubject.onNext(Pair(true, ""))
                        } else {
                            asyncSubject.onNext(Pair(true, items.first()))
                        }
                        asyncSubject.onComplete()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        asyncSubject.onNext(Pair(false, ""))
                        asyncSubject.onComplete()
                    }
                })

        return asyncSubject.flatMap { (success, chatId) ->
            if (success) {
                Observable.just(chatId)
            } else {
                Observable.error(Exception())
            }
        }
    }

    override fun getDialogs(child: String): Observable<DialogModel> {
        return getAllDialogs(child).flatMapObservable {
            Observable.fromIterable(it)
                    .flatMap { dialog ->
                        val id = when (child) {
                            "professor_id" -> dialog.studentId.orEmpty()
                            else -> dialog.teacherId.orEmpty()
                        }
                        Observable.zip(
                                messagesRepository.getLastMessage(dialog.id.orEmpty()),
                                usersRepository.getUserById(id).toObservable(),
                                BiFunction<MessageModel, UserItem, DialogModel> { t1, t2 ->
                                    DialogModelMapper.map(t1, t2, dialog.id.orEmpty())
                                })
                    }
        }
    }

    private fun getAllDialogs(child: String): Single<List<CreateDialogModel>> {
        val asyncSubject = AsyncSubject.create<Pair<Boolean, List<CreateDialogModel>>>()

        val ref = firebaseDB.getReference("chats")

        ref
                .orderByChild(child)
                .equalTo(firebaseAuth.currentUser?.uid.orEmpty())
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val items = mutableListOf<CreateDialogModel>()

                        snapshot.children.forEach { sp ->
                            val item = sp.getValue(CreateDialogItem::class.java)
                            item?.let { items.add(CreateDialogModel(sp.key, item.student_id, item.professor_id)) }
                        }

                        asyncSubject.onNext(Pair(true, items))
                        asyncSubject.onComplete()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        asyncSubject.onNext(Pair(false, emptyList()))
                        asyncSubject.onComplete()
                    }
                })

        return Single.just(isOnline(context)).flatMap { isConnected ->
            if (isConnected) {
                asyncSubject.singleOrError().flatMap { (success, items) ->
                    when {
                        success -> Single.just(items)
                        else -> Single.error(Exception())
                    }
                }
            } else {
                Single.error(Exception())
            }
        }
    }
}
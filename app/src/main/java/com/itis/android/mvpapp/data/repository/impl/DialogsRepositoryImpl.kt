package com.itis.android.mvpapp.data.repository.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.itis.android.mvpapp.data.network.pojo.firebase.response.CreateDialogItem
import com.itis.android.mvpapp.data.network.pojo.firebase.response.UserItem
import com.itis.android.mvpapp.data.repository.DialogsRepository
import com.itis.android.mvpapp.data.repository.MessagesRepository
import com.itis.android.mvpapp.data.repository.UserRepository
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
    lateinit var usersRepository: UserRepository

    override fun createDialog(studentId: String): Observable<String> {
        val asyncSubject = AsyncSubject.create<Pair<Boolean, String>>()

        val d = checkIfExistsChat(studentId, firebaseAuth.currentUser?.uid.orEmpty())
                .subscribe({ chatId ->
                    if (chatId.isEmpty()) {
                        val ref = firebaseDB.getReference("chats").push()
                        ref.setValue(CreateDialogItem(firebaseAuth.uid, studentId)).addOnCompleteListener {
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

        firebaseDB
                .getReference("chats")
                .orderByChild("professor_id")
                .equalTo(teacherId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val items = mutableListOf<String>()

                        snapshot.children.forEach { ds ->
                            val item = ds.getValue(CreateDialogItem::class.java)
                            if (item?.student_id == studentId) {
                                items.add(ds.key.orEmpty())
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

    override fun getDialogs(): Observable<DialogModel> {
        return getAllDialogs().flatMapObservable {
            Observable.fromIterable(it)
                    .flatMap { dialog ->
                        Observable.zip(
                                messagesRepository.getLastMessage(dialog.id.orEmpty()),
                                usersRepository.getUserById(dialog.studentId.orEmpty()).toObservable(),
                                BiFunction<MessageModel, UserItem, DialogModel> { t1, t2 ->
                                    DialogModelMapper.map(t1, t2, dialog.id.orEmpty())
                                })
                    }
        }
    }

    private fun getAllDialogs(): Single<List<CreateDialogModel>> {
        val asyncSubject = AsyncSubject.create<Pair<Boolean, List<CreateDialogModel>>>()

        val ref = firebaseDB.getReference("chats")

        ref
                .orderByChild("professor_id")
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

        return asyncSubject.singleOrError().flatMap { (success, items) ->
            if (success) {
                Single.just(items)
            } else {
                Single.error(Exception())
            }
        }
    }
}
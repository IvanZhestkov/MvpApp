package com.itis.android.mvpapp.data.repository.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.itis.android.mvpapp.data.network.pojo.firebase.response.MessageItem
import com.itis.android.mvpapp.data.repository.MessagesRepository
import com.itis.android.mvpapp.presentation.model.AddMessageModel
import com.itis.android.mvpapp.presentation.model.MessageModel
import com.itis.android.mvpapp.presentation.model.MessageModelMapper
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.AsyncSubject
import java.lang.Exception
import javax.inject.Inject

class MessagesRepositoryImpl @Inject constructor() : MessagesRepository {

    @Inject
    lateinit var firebaseDB: FirebaseDatabase

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun getMessages(chatId: String): Observable<MessageModel> {
        val asyncSubject = AsyncSubject.create<Pair<Boolean, List<MessageItem>>>()

        val ref = firebaseDB.getReference("messages")

        ref.child(chatId).orderByKey().addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = mutableListOf<MessageItem>()

                snapshot.children.forEach { sp ->
                    val item = sp.getValue(MessageItem::class.java)
                    item?.let { items.add(it) }
                }

                asyncSubject.onNext(Pair(true, items))
                asyncSubject.onComplete()
            }

            override fun onCancelled(p0: DatabaseError) {
                asyncSubject.onNext(Pair(false, emptyList()))
                asyncSubject.onComplete()
            }
        })

        return asyncSubject.flatMap { (success, messages) ->
            if (success) {
                Observable.fromIterable(messages)
                    .map { MessageModelMapper.map(it, firebaseAuth.currentUser?.uid.orEmpty()) }
            } else {
                Observable.error(Exception())
            }
        }
    }

    override fun getLastMessage(chatId: String): Observable<MessageModel> {
        val asyncSubject = AsyncSubject.create<Pair<Boolean, MessageItem>>()

        val ref = firebaseDB.getReference("messages")

        ref
            .child(chatId)
            .orderByChild("created_date")
            .limitToLast(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val sp = snapshot.children.lastOrNull()
                    val item = sp?.getValue(MessageItem::class.java)

                    asyncSubject.onNext(Pair(true, item ?: MessageItem()))
                    asyncSubject.onComplete()
                }

                override fun onCancelled(p0: DatabaseError) {
                    asyncSubject.onNext(Pair(false, MessageItem()))
                    asyncSubject.onComplete()
                }
            })

        return asyncSubject.flatMap { (success, message) ->
            if (success) {
                Observable.just(message).map { MessageModelMapper.map(it, firebaseAuth.currentUser?.uid.orEmpty()) }
            } else {
                Observable.error(Exception())
            }
        }
    }

    override fun addMessage(addMessageModel: AddMessageModel): Completable {
        val asyncSubject = AsyncSubject.create<Boolean>()

        val ref = firebaseDB.getReference("messages")
        ref.child(addMessageModel.chatId.orEmpty()).push().setValue(
            MessageItem(
                addMessageModel.content,
                System.currentTimeMillis(),
                firebaseAuth.currentUser?.uid.orEmpty()
            )
        ).addOnCompleteListener {
            asyncSubject.onNext(it.isSuccessful)
            asyncSubject.onComplete()
        }.addOnCanceledListener {
            asyncSubject.onNext(false)
            asyncSubject.onComplete()
        }

        return asyncSubject.flatMapCompletable { success ->
            if (success) {
                Completable.complete()
            } else {
                Completable.error(Exception())
            }
        }
    }
}
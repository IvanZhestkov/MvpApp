package com.itis.android.mvpapp.presentation.ui.teacher.dialogs.selected

import android.support.v7.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.itis.android.mvpapp.data.network.pojo.firebase.response.MessageItem
import com.itis.android.mvpapp.presentation.adapter.DialogAdapter
import com.itis.android.mvpapp.presentation.model.MessageModel
import com.itis.android.mvpapp.presentation.model.MessageModelMapper
import dagger.Module
import dagger.Provides

@Module
class DialogModule {

    @Provides
    fun getDialogId(fragment: DialogFragment) = fragment.getDialogId()


    @Provides
    fun provideAdapterOptions(
        fragment: DialogFragment,
        firebaseDb: FirebaseDatabase,
        firebaseAuth: FirebaseAuth
    ): FirebaseRecyclerOptions<MessageModel> {
        val parser = SnapshotParser { snapshot ->
            val item = snapshot.getValue(MessageItem::class.java) ?: MessageItem()
            MessageModelMapper.map(item, firebaseAuth.currentUser?.uid.orEmpty())
        }

        val ref = firebaseDb.getReference("messages").child(fragment.getDialogId())

        return FirebaseRecyclerOptions.Builder<MessageModel>()
            .setQuery(ref, parser)
            .build()
    }

    @Provides
    fun provideAdapter(options: FirebaseRecyclerOptions<MessageModel>) = DialogAdapter(options)
}
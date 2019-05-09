package com.itis.android.mvpapp.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.DatabaseError
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.data.network.pojo.firebase.response.MessageItem
import com.itis.android.mvpapp.presentation.model.MessageFromType
import com.itis.android.mvpapp.presentation.model.MessageModel
import com.itis.android.mvpapp.presentation.util.extensions.toPresentationDate
import com.itis.android.mvpapp.presentation.util.extensions.toPresentationHourMinute
import kotlinx.android.synthetic.main.item_message_header.view.*
import kotlinx.android.synthetic.main.item_message_left.view.*
import kotlinx.android.synthetic.main.item_message_right.view.*

class DialogAdapter(options: FirebaseRecyclerOptions<MessageModel>) :
        FirebaseRecyclerAdapter<MessageModel, RecyclerView.ViewHolder>(options) {

    companion object {
        const val TEXT_MESSAGE_RIGHT = 0
        const val TEXT_MESSAGE_LEFT = 1
        const val DATE_HEADER = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val v: View?
        return when (viewType) {
            TEXT_MESSAGE_LEFT -> {
                v = LayoutInflater.from(parent.context).inflate(R.layout.item_message_left, parent, false)
                MessageLeftViewHolder(v)
            }
            TEXT_MESSAGE_RIGHT -> {
                v = LayoutInflater.from(parent.context).inflate(R.layout.item_message_right, parent, false)
                MessageRightViewHolder(v)
            }
            else -> {
                v = LayoutInflater.from(parent.context).inflate(R.layout.item_message_header, parent, false)
                DateHeaderViewHolder(v)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, model: MessageModel) {
        (holder as? MessageRightViewHolder)?.bindViewHolder(model)
        when (holder.itemViewType) {
            DATE_HEADER -> (holder as? DateHeaderViewHolder)?.bindViewHolder(model)
            TEXT_MESSAGE_LEFT -> (holder as? MessageLeftViewHolder)?.bindViewHolder(model)
            TEXT_MESSAGE_RIGHT -> (holder as? MessageRightViewHolder)?.bindViewHolder(model)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position) as? MessageModel

        return when {
            item?.messageFrom == MessageFromType.ME -> TEXT_MESSAGE_RIGHT
            item?.messageFrom == MessageFromType.OTHER -> TEXT_MESSAGE_LEFT
            else -> DATE_HEADER
        }
    }


    inner class DateHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindViewHolder(item: MessageModel) = with(itemView) {
            tv_item_chat_date_header.text = item.createdDate?.toPresentationDate()
        }
    }

    inner class MessageLeftViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindViewHolder(item: MessageModel) = with(itemView) {
            tv_item_chat_left_text_message.text = item.content
            tv_item_chat_left_time.text = item.createdDate?.toPresentationHourMinute()
        }
    }

    inner class MessageRightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindViewHolder(item: MessageModel) = with(itemView) {
            tv_item_chat_right_text_message.text = item.content
            tv_item_chat_right_time.text = item.createdDate?.toPresentationHourMinute()
        }
    }
}
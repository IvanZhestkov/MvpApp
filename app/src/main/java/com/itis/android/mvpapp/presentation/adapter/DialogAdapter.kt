package com.itis.android.mvpapp.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.model.TextMessageModel
import kotlinx.android.synthetic.main.item_message_header.view.*
import kotlinx.android.synthetic.main.item_message_left.view.*
import kotlinx.android.synthetic.main.item_message_right.view.*

class DialogAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<TextMessageModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    companion object {
        const val TEXT_MESSAGE_RIGHT = 0
        const val TEXT_MESSAGE_LEFT = 1
        const val DATE_HEADER = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val v: View

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

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            DATE_HEADER -> (holder as? DateHeaderViewHolder)?.bindViewHolder()
            TEXT_MESSAGE_LEFT -> (holder as? MessageLeftViewHolder)?.bindViewHolder()
            TEXT_MESSAGE_RIGHT -> (holder as? MessageRightViewHolder)?.bindViewHolder()
        }
    }

    override fun getItemViewType(position: Int): Int {
        when {
            items[position].text.isNullOrEmpty() -> return DATE_HEADER
            items[position].to.equals("staff") -> return TEXT_MESSAGE_RIGHT
            items[position].from.equals("staff") -> return TEXT_MESSAGE_LEFT
        }

        return 0
    }

    inner class DateHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindViewHolder() = with(itemView) {
            val item = items[adapterPosition]

            tv_item_chat_date_header.text = item.dateSend
        }
    }

    inner class MessageLeftViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindViewHolder() = with(itemView) {
            val item = items[adapterPosition]

            tv_item_chat_left_text_message.text = item.text
            tv_item_chat_left_time.text = item.dateSend
        }
    }

    inner class MessageRightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindViewHolder() = with(itemView) {
            val item = items[adapterPosition]

            tv_item_chat_right_text_message.text = item.text
            tv_item_chat_right_time.text = item.dateSend
        }
    }
}
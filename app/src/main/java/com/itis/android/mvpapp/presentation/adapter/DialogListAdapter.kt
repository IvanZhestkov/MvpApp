package com.itis.android.mvpapp.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.model.DialogModel
import kotlinx.android.synthetic.main.item_dialog.view.*

class DialogListAdapter : RecyclerView.Adapter<DialogListAdapter.DialogViewHolder>() {

    var items: List<DialogModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onChatClick: ((DialogModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_dialog, parent, false)
        return DialogViewHolder(v)
    }

    override fun onBindViewHolder(holder: DialogViewHolder, position: Int) {
        holder.bindViewHolder()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class DialogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindViewHolder() = with(itemView) {
            val item = items[adapterPosition]

            setOnClickListener { onChatClick?.invoke(item) }

            dialog_last_message.text = item.lastMessage?.text
            dialog_time.text = item.lastMessage?.dateSend
            dialog_title.text = item.dialogName

            if (item.lastMessage?.to == "staff") {
                dialog_you.visibility = View.VISIBLE
            } else {
                dialog_you.visibility = View.GONE
            }
        }
    }
}

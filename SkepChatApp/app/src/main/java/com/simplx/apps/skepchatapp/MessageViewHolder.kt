package com.simplx.apps.skepchatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView


class MessageViewHolder(private val context: Context) :
    RecyclerView.Adapter<MessageViewHolder.MessageViewHolder>() {

    private var messageList: List<Message> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    fun setList(list: List<Message>) {
        this.messageList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        // holder.messageTextView?.text = messageList[position].message
    }

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var messageTextView: TextView? = null
        var messageImageView: ImageView? = null
        var messengerTextView: TextView? = null
        var messengerImageView: CircleImageView? = null

        init {
            messageTextView =
                itemView.findViewById<View>(R.id.messageTextView) as TextView?
            messageImageView =
                itemView.findViewById<View>(R.id.messageImageView) as ImageView?
            messengerTextView =
                itemView.findViewById<View>(R.id.messengerTextView) as TextView?
            messengerImageView =
                itemView.findViewById<View>(R.id.messengerImageView) as CircleImageView?
        }
    }
}



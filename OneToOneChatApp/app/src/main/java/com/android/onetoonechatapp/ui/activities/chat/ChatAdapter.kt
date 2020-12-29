package com.android.onetoonechatapp.ui.activities.chat

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.onetoonechatapp.R
import com.android.onetoonechatapp.models.Chat
import com.google.firebase.auth.FirebaseAuth

class ChatAdapter(
    private val onUserClickListener: OnUserClickListener
) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    private var mChats: ArrayList<Chat> = ArrayList()

    companion object {
        private const val VIEW_TYPE_ME = 1
        private const val VIEW_TYPE_OTHER = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        var viewHolder: ChatViewHolder? = null

        when (viewType) {
            VIEW_TYPE_ME -> {
                val viewChatMine: View =
                    layoutInflater.inflate(R.layout.item_chat_mine, parent, false)
                viewHolder = ChatViewHolder(viewChatMine, onUserClickListener)
            }

            VIEW_TYPE_OTHER -> {
                val viewChatOther: View =
                    layoutInflater.inflate(R.layout.item_chat_other, parent, false)
                viewHolder = ChatViewHolder(viewChatOther, onUserClickListener)
            }
        }

        return viewHolder!!
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        if (TextUtils.equals(
                mChats[position].senderUid, FirebaseAuth.getInstance().currentUser?.uid
            )
        ) {
            configureMyChatViewHolder(holder, position)
        } else {
            configureOtherChatViewHolder((holder), position)
        }
    }

    private fun configureMyChatViewHolder(myChatViewHolder: ChatViewHolder, position: Int) {

        val chat = mChats[position]
        val sender = mChats[position].sender

        val alphabet = sender!!.substring(0, 1)
        myChatViewHolder.txtChatMessage?.text = chat.message
        myChatViewHolder.txtUserAlphabet?.text = alphabet
    }

    private fun configureOtherChatViewHolder(
        otherChatViewHolder: ChatViewHolder,
        position: Int
    ) {
        val chat = mChats[position]
        val sender = mChats[position].sender
        val alphabet = sender?.substring(0, 1)
        otherChatViewHolder.txtChatMessage?.text = chat.message
        otherChatViewHolder.txtUserAlphabet?.text = alphabet
    }

    override fun getItemCount(): Int {
        return mChats.size
    }

    fun add(chat: Chat) {
        mChats.add(chat)
        mChats.size.minus(1).let { notifyItemInserted(it) }
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (TextUtils.equals(
                mChats[position].senderUid,
                FirebaseAuth.getInstance().currentUser?.uid
            )
        ) {
            VIEW_TYPE_ME
        } else {
            VIEW_TYPE_OTHER
        }
    }

    class ChatViewHolder(itemView: View, listener: OnUserClickListener) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private var onUserClickListener: OnUserClickListener? = null

        var txtChatMessage: TextView? = null
        var txtUserAlphabet: TextView? = null

        init {
            txtChatMessage = itemView.findViewById(R.id.text_view_chat_message)
            txtUserAlphabet = itemView.findViewById(R.id.text_view_user_alphabet)

            this.onUserClickListener = listener
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onUserClickListener?.onUserClick(adapterPosition)
        }
    }

    interface OnUserClickListener {
        fun onUserClick(position: Int)
    }

}
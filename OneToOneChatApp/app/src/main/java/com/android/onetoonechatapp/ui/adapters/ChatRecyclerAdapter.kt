package com.android.onetoonechatapp.ui.adapters

/*
import android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.onetoonechatapp.models.Chat


class ChatRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private val VIEW_TYPE_ME = 1
    private val VIEW_TYPE_OTHER = 2

    private var mChats: MutableList<Chat>? = null

    fun ChatRecyclerAdapter(chats: MutableList<Chat>) {
        mChats = chats
    }

    fun add(chat: Chat) {
        mChats!!.add(chat)
        notifyItemInserted(mChats!!.size - 1)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        var viewHolder: RecyclerView.ViewHolder? = null
        when (viewType) {
            VIEW_TYPE_ME -> {
                val viewChatMine: View =
                    layoutInflater.inflate(R.layout.item_chat_mine, parent, false)
                viewHolder = MyChatViewHolder(viewChatMine)
            }
            VIEW_TYPE_OTHER -> {
                val viewChatOther: View =
                    layoutInflater.inflate(R.layout.item_chat_other, parent, false)
                viewHolder = OtherChatViewHolder(viewChatOther)
            }
        }
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        if (mChats != null) {
            return mChats?.size
        }
        return 0;
    }

    private fun configureMyChatViewHolder(myChatViewHolder: MyChatViewHolder, position: Int) {
        val (sender, _, _, _, message) = mChats!![position]
        val alphabet = sender!!.substring(0, 1)
        myChatViewHolder.txtChatMessage.setText(message)
        myChatViewHolder.txtUserAlphabet.setText(alphabet)
    }

    private fun configureOtherChatViewHolder(
        otherChatViewHolder: OtherChatViewHolder,
        position: Int
    ) {
        val (sender, _, _, _, message) = mChats!![position]
        val alphabet = sender!!.substring(0, 1)
        otherChatViewHolder.txtChatMessage.setText(message)
        otherChatViewHolder.txtUserAlphabet.setText(alphabet)
    }


}


 */
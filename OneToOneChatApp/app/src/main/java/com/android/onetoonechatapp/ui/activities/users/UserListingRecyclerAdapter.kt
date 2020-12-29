package com.android.onetoonechatapp.ui.activities.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.onetoonechatapp.R
import com.android.onetoonechatapp.models.User

class UserListingRecyclerAdapter(private val onUserClickListener: OnNewUserClickListener) :
    RecyclerView.Adapter<UserListingRecyclerAdapter.ViewHolder>() {

    private var mUsers: ArrayList<User?>? = ArrayList()

    fun setList(l: ArrayList<User?>?) {
        this.mUsers = l
        notifyDataSetChanged()
    }

    fun add(user: User) {
        mUsers?.add(user)
        mUsers?.size?.minus(1).let {
            if (it != null) {
                notifyItemInserted(it)
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_all_user_listing, parent, false)
        return ViewHolder(view, onUserClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val email = mUsers?.get(position)?.email
        if (email != null) {
            val alphabet = email.substring(0, 1)
            holder.txtUsername?.text = email
            holder.txtUserAlphabet?.text = alphabet
        }
    }

    override fun getItemCount(): Int {
        return mUsers?.size!!
    }

    fun getUser(position: Int): User? {
        return mUsers?.get(position)
    }

    class ViewHolder(itemView: View, listener: OnNewUserClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var onUserClickListener: OnNewUserClickListener? = null

        var txtUserAlphabet: TextView? = null
        var txtUsername: TextView? = null

        init {
            txtUserAlphabet = itemView.findViewById(R.id.text_view_user_alphabet)
            txtUsername = itemView.findViewById(R.id.text_view_username)
            this.onUserClickListener = listener
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onUserClickListener?.onNewUserClick(adapterPosition)
        }
    }

    interface OnNewUserClickListener {
        fun onNewUserClick(position: Int)
    }


}
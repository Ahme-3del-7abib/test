package com.android.onetoonechatapp.ui.activities.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.onetoonechatapp.R
import com.android.onetoonechatapp.models.User

class UserListingRecyclerAdapter(users: List<User?>?) :
    RecyclerView.Adapter<UserListingRecyclerAdapter.ViewHolder>() {

    private val mUsers: ArrayList<User> = ArrayList()

    fun add(user: User) {
        mUsers.add(user)
        notifyItemInserted(mUsers.size - 1)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_all_user_listing, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (_, email) = mUsers[position]

        if (email != null) {
            val alphabet = email.substring(0, 1)
            holder.txtUsername?.text = email
            holder.txtUserAlphabet?.text = alphabet

        }
    }

    override fun getItemCount(): Int {
        return mUsers.size
    }

    fun getUser(position: Int): User {
        return mUsers[position]
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtUserAlphabet: TextView? = null
        var txtUsername: TextView? = null

        init {
            txtUserAlphabet = itemView.findViewById(R.id.text_view_user_alphabet)
            txtUsername = itemView.findViewById(R.id.text_view_username)
        }
    }


}
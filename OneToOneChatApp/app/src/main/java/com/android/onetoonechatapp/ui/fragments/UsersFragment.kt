package com.android.onetoonechatapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.onetoonechatapp.R
import com.android.onetoonechatapp.core.users.all.GetUsersContract
import com.android.onetoonechatapp.core.users.all.GetUsersPresenter
import com.android.onetoonechatapp.models.User
import com.android.onetoonechatapp.ui.activities.chat.ChatActivity
import com.android.onetoonechatapp.ui.activities.users.UserListingRecyclerAdapter

class UsersFragment : Fragment(), GetUsersContract.View,
    UserListingRecyclerAdapter.OnNewUserClickListener,
    SwipeRefreshLayout.OnRefreshListener {

    companion object {

        const val ARG_TYPE = "type"
        const val TYPE_CHATS = "type_chats"
        const val TYPE_ALL = "type_all"

        fun newInstance(type: String?): UsersFragment {
            val args = Bundle()
            args.putString(ARG_TYPE, type)
            val fragment = UsersFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    private var mRecyclerViewAllUserListing: RecyclerView? = null
    private var mUserListingRecyclerAdapter: UserListingRecyclerAdapter? = null

    private var mGetUsersPresenter: GetUsersPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentView: View = inflater.inflate(R.layout.fragment_users, container, false)
        bindViews(fragmentView)
        return fragmentView
    }

    private fun bindViews(view: View) {
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        mRecyclerViewAllUserListing = view.findViewById(R.id.recycler_view_all_user_listing)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        mGetUsersPresenter = GetUsersPresenter(this)
        getUsers()

        mSwipeRefreshLayout?.post { mSwipeRefreshLayout?.isRefreshing = true }
        mSwipeRefreshLayout?.setOnRefreshListener(this)
        mUserListingRecyclerAdapter = UserListingRecyclerAdapter(this)

    }

    override fun onRefresh() {
        getUsers()
    }

    private fun getUsers() {
        if (TextUtils.equals(
                requireArguments().getString(ARG_TYPE), TYPE_ALL
            )
        ) {
            mGetUsersPresenter?.getAllUsers()
        }
    }

    override fun onNewUserClick(position: Int) {
        ChatActivity.startActivity(
            requireContext(), mUserListingRecyclerAdapter?.getUser(position)?.email,
            mUserListingRecyclerAdapter?.getUser(position)?.uid,
            mUserListingRecyclerAdapter?.getUser(position)?.firebaseToken
        )
    }

    override fun onGetAllUsersSuccess(users: ArrayList<User?>?) {
        mSwipeRefreshLayout?.post { mSwipeRefreshLayout?.isRefreshing = false }
        mRecyclerViewAllUserListing?.adapter = mUserListingRecyclerAdapter
        mUserListingRecyclerAdapter?.setList(users)
    }

    override fun onGetAllUsersFailure(message: String?) {
        mSwipeRefreshLayout?.post { mSwipeRefreshLayout?.isRefreshing = false }
        Toast.makeText(activity, "Error: $message", Toast.LENGTH_SHORT).show()
    }

    override fun onGetChatUsersSuccess(users: ArrayList<User?>?) {
        TODO("Not yet implemented")
    }

    override fun onGetChatUsersFailure(message: String?) {
        TODO("Not yet implemented")
    }


}
package com.android.onetoonechatapp.ui.activities.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.android.onetoonechatapp.FirebaseChatMainApp
import com.android.onetoonechatapp.R
import com.android.onetoonechatapp.ui.fragments.ChatFragment
import com.android.onetoonechatapp.ui.fragments.LoginFragment
import com.android.onetoonechatapp.utils.Constants


class ChatActivity : AppCompatActivity() {

    private var mToolbar: Toolbar? = null

    companion object {
        fun startActivity(
            context: Context,
            receiver: String?,
            receiverUid: String?,
            firebaseToken: String?
        ) {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(Constants.ARG_RECEIVER, receiver)
            intent.putExtra(Constants.ARG_RECEIVER_UID, receiverUid)
            intent.putExtra(Constants.ARG_FIREBASE_TOKEN, firebaseToken)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        bindViews()
        init()
    }

    override fun onResume() {
        super.onResume()
        FirebaseChatMainApp.setChatActivityOpen(true)
    }

    override fun onPause() {
        super.onPause()
        FirebaseChatMainApp.setChatActivityOpen(false)
    }

    private fun bindViews() {
        mToolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(mToolbar)
    }

    private fun init() {
        mToolbar!!.title = intent.extras!!.getString(Constants.ARG_RECEIVER)

        val chatFragment: Fragment = ChatFragment()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.frame_layout_content_chat,
                chatFragment,
                LoginFragment::class.java.simpleName
            ).commit()
    }

}
package com.android.onetoonechatapp.ui.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.android.onetoonechatapp.R
import com.android.onetoonechatapp.core.chat.ChatContract
import com.android.onetoonechatapp.core.chat.ChatPresenter
import com.android.onetoonechatapp.events.PushNotificationEvent
import com.android.onetoonechatapp.models.Chat
import com.android.onetoonechatapp.ui.activities.chat.ChatAdapter
import com.android.onetoonechatapp.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class ChatFragment : Fragment(), ChatContract.View, TextView.OnEditorActionListener,
    ChatAdapter.OnUserClickListener {

    private var mRecyclerViewChat: RecyclerView? = null
    private var mETxtMessage: EditText? = null

    private var mProgressDialog: ProgressDialog? = null

    private val chatAdapter = ChatAdapter(this)

    private var mChatPresenter: ChatPresenter? = null

    companion object {
        fun newInstance(
            receiver: String?,
            receiverUid: String?,
            firebaseToken: String?
        ): ChatFragment {
            val args = Bundle()
            args.putString(Constants.ARG_RECEIVER, receiver)
            args.putString(Constants.ARG_RECEIVER_UID, receiverUid)
            args.putString(Constants.ARG_FIREBASE_TOKEN, firebaseToken)
            val fragment = ChatFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView: View = inflater.inflate(R.layout.fragment_chat, container, false)
        bindViews(fragmentView)
        return fragmentView
    }

    private fun bindViews(view: View) {
        mRecyclerViewChat = view.findViewById(R.id.recycler_view_chat)
        mETxtMessage = view.findViewById(R.id.edit_text_message)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        mProgressDialog = ProgressDialog(activity)
        mProgressDialog?.setTitle(getString(R.string.loading))
        mProgressDialog?.setMessage(getString(R.string.please_wait))
        mProgressDialog?.isIndeterminate = true
        mETxtMessage!!.setOnEditorActionListener(this)
        mChatPresenter = ChatPresenter(this)
        mChatPresenter?.getMessage(
            FirebaseAuth.getInstance().currentUser!!.uid,
            requireArguments().getString(Constants.ARG_RECEIVER_UID)
        )
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            sendMessage()
            return true
        }
        return false
    }

    private fun sendMessage() {
        val message = mETxtMessage?.text.toString()
        val receiver = arguments?.getString(Constants.ARG_RECEIVER)
        val receiverUid = arguments?.getString(Constants.ARG_RECEIVER_UID)
        val sender = FirebaseAuth.getInstance().currentUser?.email
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        val receiverFirebaseToken = arguments?.getString(Constants.ARG_FIREBASE_TOKEN)

        val chat = Chat(
            sender,
            receiver,
            senderUid,
            receiverUid,
            message,
            System.currentTimeMillis()
        )

        mChatPresenter?.sendMessage(
            activity?.applicationContext,
            chat,
            receiverFirebaseToken
        )
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onSendMessageSuccess() {
        Toast.makeText(activity, "Message sent", Toast.LENGTH_SHORT).show()
    }

    override fun onSendMessageFailure(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onGetMessagesSuccess(chat: Chat) {
        chatAdapter.add(chat)
        mRecyclerViewChat?.adapter = chatAdapter
        mRecyclerViewChat?.smoothScrollToPosition(chatAdapter.itemCount - 1)
    }

    override fun onGetMessagesFailure(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    @Subscribe
    fun onPushNotificationEvent(pushNotificationEvent: PushNotificationEvent) {
        if (chatAdapter.itemCount == 0) {
            mChatPresenter?.getMessage(
                FirebaseAuth.getInstance().currentUser!!.uid,
                pushNotificationEvent.uid
            )
        }
    }

    override fun onUserClick(position: Int) {
        Toast.makeText(requireActivity(), "hello", Toast.LENGTH_SHORT).show()
    }
}
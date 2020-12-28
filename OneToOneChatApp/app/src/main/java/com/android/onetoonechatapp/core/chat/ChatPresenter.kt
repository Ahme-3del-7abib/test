package com.android.onetoonechatapp.core.chat

import android.content.Context
import com.android.onetoonechatapp.models.Chat

class ChatPresenter(
    private val mView: ChatContract.View,
    private val mChatInteractor: ChatInteractor

) : ChatContract.Presenter, ChatContract.OnSendMessageListener, ChatContract.OnGetMessagesListener {


    override fun sendMessage(context: Context?, chat: Chat?, receiverFirebaseToken: String?) {
        mChatInteractor.sendMessageToFirebaseUser(context, chat, receiverFirebaseToken)
    }

    override fun getMessage(senderUid: String?, receiverUid: String?) {
        mChatInteractor.getMessageFromFirebaseUser(senderUid, receiverUid)
    }

    override fun onSendMessageSuccess() {
        mView.onSendMessageSuccess()
    }

    override fun onSendMessageFailure(message: String?) {
        if (message != null) {
            mView.onSendMessageFailure(message)
        }
    }

    override fun onGetMessagesSuccess(chat: Chat?) {
        if (chat != null) {
            mView.onGetMessagesSuccess(chat)
        }
    }

    override fun onGetMessagesFailure(message: String?) {
        if (message != null) {
            mView.onGetMessagesFailure(message)
        }
    }
}
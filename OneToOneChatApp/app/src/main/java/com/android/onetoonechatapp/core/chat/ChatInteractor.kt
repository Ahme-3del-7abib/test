package com.android.onetoonechatapp.core.chat

import android.content.Context
import android.util.Log
import com.android.onetoonechatapp.core.chat.ChatContract.OnGetMessagesListener
import com.android.onetoonechatapp.core.chat.ChatContract.OnSendMessageListener
import com.android.onetoonechatapp.fcm.FcmNotificationBuilder
import com.android.onetoonechatapp.models.Chat
import com.android.onetoonechatapp.utils.Constants
import com.android.onetoonechatapp.utils.SharedPrefUtil
import com.google.firebase.database.*

class ChatInteractor(
    private var mOnSendMessageListener: OnSendMessageListener,
    var mOnGetMessagesListener: OnGetMessagesListener
) : ChatContract.Interactor {

    private val TAG = "ChatInteractor"

    override fun sendMessageToFirebaseUser(
        context: Context?,
        chat: Chat?,
        receiverFirebaseToken: String?
    ) {

        val room_type_1 = chat?.senderUid.toString() + "_" + chat?.receiverUid
        val room_type_2 = chat?.receiverUid.toString() + "_" + chat?.senderUid

        val databaseReference = FirebaseDatabase.getInstance().reference

        databaseReference.child(Constants.ARG_CHAT_ROOMS).ref.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.hasChild(room_type_1)) {
                    databaseReference.child(Constants.ARG_CHAT_ROOMS).child(room_type_1)
                        .child(java.lang.String.valueOf(chat?.timeStamp)).setValue(chat)

                } else if (dataSnapshot.hasChild(room_type_2)) {
                    databaseReference.child(Constants.ARG_CHAT_ROOMS).child(room_type_2)
                        .child(java.lang.String.valueOf(chat?.timeStamp)).setValue(chat)

                } else {
                    databaseReference.child(Constants.ARG_CHAT_ROOMS).child(room_type_1)
                        .child(java.lang.String.valueOf(chat?.timeStamp)).setValue(chat)

                    getMessageFromFirebaseUser(chat?.senderUid, chat?.receiverUid)
                }
                // send push notification to the receiver
                if (context != null) {
                    SharedPrefUtil(context).getString(Constants.ARG_FIREBASE_TOKEN)?.let {
                        sendPushNotificationToReceiver(
                            chat?.sender!!,
                            chat.message!!,
                            chat.senderUid!!,
                            it,
                            receiverFirebaseToken!!
                        )
                    }
                }

                mOnSendMessageListener.onSendMessageSuccess()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                mOnSendMessageListener.onSendMessageFailure("Unable to send message: " + databaseError.message)
            }
        })
    }

    private fun sendPushNotificationToReceiver(
        username: String,
        message: String,
        uid: String,
        firebaseToken: String,
        receiverFirebaseToken: String
    ) {
        FcmNotificationBuilder.initialize()
            .title(username)
            .message(message)
            .username(username)
            .uid(uid)
            .firebaseToken(firebaseToken)
            .receiverFirebaseToken(receiverFirebaseToken)
            .send()
    }

    override fun getMessageFromFirebaseUser(senderUid: String?, receiverUid: String?) {
        val room_type_1 = senderUid.toString() + "_" + receiverUid
        val room_type_2 = receiverUid.toString() + "_" + senderUid

        val databaseReference = FirebaseDatabase.getInstance().reference

        databaseReference.child(Constants.ARG_CHAT_ROOMS).ref.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.hasChild(room_type_1)) {
                    Log.e(TAG, "getMessageFromFirebaseUser: $room_type_1 exists")
                    FirebaseDatabase.getInstance()
                        .reference
                        .child(Constants.ARG_CHAT_ROOMS)
                        .child(room_type_1).addChildEventListener(object : ChildEventListener {
                            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                                val chat = dataSnapshot.getValue(Chat::class.java)
                                mOnGetMessagesListener.onGetMessagesSuccess(chat)
                            }

                            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
                            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
                            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
                            override fun onCancelled(databaseError: DatabaseError) {
                                mOnGetMessagesListener.onGetMessagesFailure("Unable to get message: " + databaseError.message)
                            }
                        })
                } else if (dataSnapshot.hasChild(room_type_2)) {
                    Log.e(TAG, "getMessageFromFirebaseUser: $room_type_2 exists")
                    FirebaseDatabase.getInstance()
                        .reference
                        .child(Constants.ARG_CHAT_ROOMS)
                        .child(room_type_2).addChildEventListener(object : ChildEventListener {
                            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                                val chat = dataSnapshot.getValue(Chat::class.java)
                                mOnGetMessagesListener.onGetMessagesSuccess(chat)
                            }

                            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
                            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
                            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
                            override fun onCancelled(databaseError: DatabaseError) {
                                mOnGetMessagesListener.onGetMessagesFailure("Unable to get message: " + databaseError.message)
                            }
                        })
                } else {
                    Log.e(TAG, "getMessageFromFirebaseUser: no such room available")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                mOnGetMessagesListener.onGetMessagesFailure("Unable to get message: " + databaseError.message)
            }
        })
    }

}
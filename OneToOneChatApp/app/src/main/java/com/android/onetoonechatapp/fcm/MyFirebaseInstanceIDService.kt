package com.android.onetoonechatapp.fcm

import android.util.Log
import com.android.onetoonechatapp.utils.Constants
import com.android.onetoonechatapp.utils.SharedPrefUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {

    private val TAG = "MyFirebaseIIDService"

    override fun onTokenRefresh() {
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(TAG, "Refreshed token: $refreshedToken")

        sendRegistrationToServer(refreshedToken)
    }

    private fun sendRegistrationToServer(token: String?) {
        SharedPrefUtil(applicationContext).saveString(Constants.ARG_FIREBASE_TOKEN, token)
        if (FirebaseAuth.getInstance().currentUser != null) {
            FirebaseAuth.getInstance().currentUser?.let {
                FirebaseDatabase.getInstance()
                    .reference
                    .child(Constants.ARG_USERS)
                    .child(it.uid)
                    .child(Constants.ARG_FIREBASE_TOKEN)
                    .setValue(token)
            }
        }
    }
}
package com.android.onetoonechatapp.core.login

import android.app.Activity
import com.android.onetoonechatapp.core.login.LoginContract.OnLoginListener
import com.android.onetoonechatapp.utils.Constants
import com.android.onetoonechatapp.utils.SharedPrefUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class LoginInteractor(private val mOnLoginListener: OnLoginListener) : LoginContract.Interactor {


    override fun performFirebaseLogin(activity: Activity?, email: String?, password: String?) {
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener(
                activity!!
            ) { task ->

                if (task.isSuccessful) {
                    mOnLoginListener.onSuccess(task.result.toString())
                    task.result?.user?.uid?.let {
                        updateFirebaseToken(
                            it,
                            SharedPrefUtil(activity!!.applicationContext).getString(
                                Constants.ARG_FIREBASE_TOKEN,
                                null
                            )
                        )
                    }
                } else {
                    mOnLoginListener.onFailure(task.exception?.message)
                }
            }
    }

    private fun updateFirebaseToken(uid: String, token: String?) {
        FirebaseDatabase.getInstance()
            .reference
            .child(Constants.ARG_USERS)
            .child(uid)
            .child(Constants.ARG_FIREBASE_TOKEN)
            .setValue(token)
    }

}
package com.android.onetoonechatapp.core.registeration

import android.R.attr
import android.app.Activity
import com.android.onetoonechatapp.core.registeration.RegisterContract.OnRegistrationListener
import com.google.firebase.auth.FirebaseAuth


class RegisterInteractor(private val mOnRegistrationListener: OnRegistrationListener) :
    RegisterContract.Interactor {

    override fun performFirebaseRegistration(
        activity: Activity?,
        email: String?,
        password: String?
    ) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener(
                activity!!
            ) { task ->

                if (!task.isSuccessful) {
                    mOnRegistrationListener.onFailure(task.exception?.message)
                } else {
                    mOnRegistrationListener.onSuccess(task.result?.getUser())
                }
            }
    }
}
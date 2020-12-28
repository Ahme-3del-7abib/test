package com.android.onetoonechatapp.core.registeration

import android.app.Activity
import com.google.firebase.auth.FirebaseUser

class RegisterPresenter(
    private val mRegisterView: RegisterContract.View,
    private val mRegisterInteractor: RegisterInteractor
) : RegisterContract.Presenter, RegisterContract.OnRegistrationListener {

    override fun register(activity: Activity?, email: String?, password: String?) {
        mRegisterInteractor.performFirebaseRegistration(activity, email, password)
    }

    override fun onSuccess(firebaseUser: FirebaseUser?) {
        mRegisterView.onRegistrationSuccess(firebaseUser)
    }

    override fun onFailure(message: String?) {
        mRegisterView.onRegistrationFailure(message)
    }

}
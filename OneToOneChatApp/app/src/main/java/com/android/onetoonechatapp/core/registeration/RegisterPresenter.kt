package com.android.onetoonechatapp.core.registeration

import android.app.Activity
import com.google.firebase.auth.FirebaseUser

class RegisterPresenter(
    private val mRegisterView: RegisterContract.View

) : RegisterContract.Presenter, RegisterContract.OnRegistrationListener {

    private val mRegisterInteractor: RegisterInteractor = RegisterInteractor(this)

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
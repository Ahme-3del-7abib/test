package com.android.onetoonechatapp.core.login

import android.app.Activity

class LoginPresenter(
    private val mLoginView: LoginContract.View
) : LoginContract.Presenter, LoginContract.OnLoginListener {

    private val mLoginInteractor: LoginInteractor = LoginInteractor(this)

    override fun login(activity: Activity?, email: String?, password: String?) {
        mLoginInteractor.performFirebaseLogin(activity, email, password)

    }

    override fun onSuccess(message: String?) {
        mLoginView.onLoginSuccess(message)
    }

    override fun onFailure(message: String?) {
        mLoginView.onLoginFailure(message)
    }
}
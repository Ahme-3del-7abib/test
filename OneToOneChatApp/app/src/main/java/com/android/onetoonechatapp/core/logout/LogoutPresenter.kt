package com.android.onetoonechatapp.core.logout

class LogoutPresenter(
    private val mLogoutView: LogoutContract.View?,
    private val mLogoutInteractor: LogoutInteractor
) : LogoutContract.Presenter, LogoutContract.OnLogoutListener {

    override fun logout() {
        mLogoutInteractor.performFirebaseLogout()
    }

    override fun onSuccess(message: String?) {
        mLogoutView?.onLogoutSuccess(message)
    }

    override fun onFailure(message: String?) {
        mLogoutView?.onLogoutFailure(message)
    }
}
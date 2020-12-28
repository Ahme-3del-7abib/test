package com.android.onetoonechatapp.core.logout

class LogoutPresenter(
    private val mLogoutView: LogoutContract.View?
) : LogoutContract.Presenter, LogoutContract.OnLogoutListener {

    var mLogoutInteractor: LogoutInteractor = LogoutInteractor(this)

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
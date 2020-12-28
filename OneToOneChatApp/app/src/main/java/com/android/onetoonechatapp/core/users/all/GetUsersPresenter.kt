package com.android.onetoonechatapp.core.users.all

import com.android.onetoonechatapp.models.User

class GetUsersPresenter(
    private val mView: GetUsersContract.View,
    private val mGetUsersInteractor: GetUsersInteractor
) : GetUsersContract.Presenter, GetUsersContract.OnGetAllUsersListener {

    override fun getAllUsers() {
        mGetUsersInteractor.getAllUsersFromFirebase()
    }

    override fun getChatUsers() {
        mGetUsersInteractor.getChatUsersFromFirebase()
    }

    override fun onGetAllUsersSuccess(users: List<User?>?) {
        mView.onGetAllUsersSuccess(users)
    }

    override fun onGetAllUsersFailure(message: String?) {
        mView.onGetAllUsersFailure(message)
    }

}
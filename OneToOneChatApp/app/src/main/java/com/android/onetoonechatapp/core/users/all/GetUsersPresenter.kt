package com.android.onetoonechatapp.core.users.all

import com.android.onetoonechatapp.models.User

class GetUsersPresenter(
    private val mView: GetUsersContract.View,
) : GetUsersContract.Presenter, GetUsersContract.OnGetAllUsersListener {

    private val mGetUsersInteractor: GetUsersInteractor = GetUsersInteractor(this)

    override fun getAllUsers() {
        mGetUsersInteractor.getAllUsersFromFirebase()
    }

    override fun getChatUsers() {
        mGetUsersInteractor.getChatUsersFromFirebase()
    }

    override fun onGetAllUsersSuccess(users: ArrayList<User?>?) {
        mView.onGetAllUsersSuccess(users)
    }

    override fun onGetAllUsersFailure(message: String?) {
        mView.onGetAllUsersFailure(message)
    }

}
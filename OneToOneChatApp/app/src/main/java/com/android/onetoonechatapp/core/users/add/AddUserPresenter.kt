package com.android.onetoonechatapp.core.users.add

import android.content.Context
import com.google.firebase.auth.FirebaseUser

class AddUserPresenter(
    private val mView: AddUserContract.View,
    private val mAddUserInteractor: AddUserInteractor
) : AddUserContract.Presenter, AddUserContract.OnUserDatabaseListener {

    override fun addUser(context: Context?, firebaseUser: FirebaseUser?) {
        mAddUserInteractor.addUserToDatabase(context, firebaseUser)
    }

    override fun onSuccess(message: String?) {
        mView.onAddUserSuccess(message)
    }

    override fun onFailure(message: String?) {
        mView.onAddUserFailure(message)
    }

}
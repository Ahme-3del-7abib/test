package com.android.onetoonechatapp.core.users.add

import android.content.Context
import com.android.onetoonechatapp.R
import com.android.onetoonechatapp.core.users.add.AddUserContract.OnUserDatabaseListener
import com.android.onetoonechatapp.models.User
import com.android.onetoonechatapp.utils.Constants
import com.android.onetoonechatapp.utils.SharedPrefUtil
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class AddUserInteractor(private val mOnUserDatabaseListener: OnUserDatabaseListener) :
    AddUserContract.Interactor {

    override fun addUserToDatabase(context: Context?, firebaseUser: FirebaseUser?) {
        val database = FirebaseDatabase.getInstance().reference
        val user = User(
            firebaseUser?.uid,
            firebaseUser?.email,
            context?.let { SharedPrefUtil(it).getString(Constants.ARG_FIREBASE_TOKEN) }
        )

        database.child(Constants.ARG_USERS)
            .child(firebaseUser!!.uid)
            .setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mOnUserDatabaseListener.onSuccess(context?.getString(R.string.user_successfully_added))
                } else {
                    mOnUserDatabaseListener.onFailure(context?.getString(R.string.user_unable_to_add))
                }
            }
    }
}
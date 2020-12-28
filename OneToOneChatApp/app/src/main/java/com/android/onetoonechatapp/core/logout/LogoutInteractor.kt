package com.android.onetoonechatapp.core.logout

import com.android.onetoonechatapp.core.logout.LogoutContract.OnLogoutListener
import com.google.firebase.auth.FirebaseAuth

class LogoutInteractor(private val mOnLogoutListener: OnLogoutListener) :
    LogoutContract.Interactor {

    override fun performFirebaseLogout() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            FirebaseAuth.getInstance().signOut();
            mOnLogoutListener.onSuccess("Successfully logged out!");
        } else {
            mOnLogoutListener.onFailure("No user logged in yet!");
        }
    }
}
package com.android.onetoonechatapp.core.registeration

import android.app.Activity
import com.google.firebase.auth.FirebaseUser


interface RegisterContract {

    interface View {
        fun onRegistrationSuccess(firebaseUser: FirebaseUser?)
        fun onRegistrationFailure(message: String?)
    }

    interface Presenter {
        fun register(activity: Activity?, email: String?, password: String?)
    }

    interface Interactor {
        fun performFirebaseRegistration(activity: Activity?, email: String?, password: String?)
    }

    interface OnRegistrationListener {
        fun onSuccess(firebaseUser: FirebaseUser?)
        fun onFailure(message: String?)
    }
}
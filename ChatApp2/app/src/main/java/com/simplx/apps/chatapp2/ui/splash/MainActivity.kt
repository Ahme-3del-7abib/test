package com.simplx.apps.chatapp2.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.simplx.apps.chatapp2.R
import com.simplx.apps.chatapp2.ui.login.SignInActivity
import com.simplx.apps.chatapp2.ui.parent.ParentActivity
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (FirebaseAuth.getInstance().currentUser == null) {
            startActivity<SignInActivity>()
        } else {
            startActivity<ParentActivity>()
        }

        finish()
    }
}
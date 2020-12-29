package com.android.onetoonechatapp.ui.activities.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.android.onetoonechatapp.R
import com.android.onetoonechatapp.ui.activities.login.LoginActivity
import com.android.onetoonechatapp.ui.activities.users.UserListActivity

import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_MS = 2000
    private var mHandler: Handler? = null
    private var mRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mHandler = Handler()
        mRunnable = Runnable {
            if (FirebaseAuth.getInstance().currentUser != null) {
                UserListActivity.startActivity(this)
            } else {
                LoginActivity.startIntent(this)
            }
            finish()
        }

        mHandler?.postDelayed(mRunnable!!, SPLASH_TIME_MS.toLong())
    }
}
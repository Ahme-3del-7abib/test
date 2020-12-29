package com.android.onetoonechatapp.ui.activities.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentTransaction
import com.android.onetoonechatapp.R
import com.android.onetoonechatapp.ui.fragments.LoginFragment


class LoginActivity : AppCompatActivity() {

    private var mToolbar: Toolbar? = null

    companion object {
        fun startIntent(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }

        fun startIntent(context: Context, flags: Int) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = flags
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        bindViews()
        init()
    }

    private fun bindViews() {
        mToolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(mToolbar)
    }

    private fun init() {

        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(
            R.id.frame_layout_content_login,
            LoginFragment.newInstance(),
            LoginFragment::class.java.simpleName
        )
        fragmentTransaction.commit()

    }
}



package com.android.onetoonechatapp.ui.activities.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.onetoonechatapp.R
import com.android.onetoonechatapp.ui.fragments.LoginFragment
import com.android.onetoonechatapp.ui.fragments.RegisterFragment


class RegisterActivity : AppCompatActivity() {

    private var mToolbar: Toolbar? = null

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        bindViews()
        init()

    }

    private fun bindViews() {
        mToolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(mToolbar)
    }

    private fun init() {
        val registerFragment: Fragment = RegisterFragment()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.frame_layout_content_register,
                registerFragment,
                LoginFragment::class.java.simpleName
            ).commit()
    }
}
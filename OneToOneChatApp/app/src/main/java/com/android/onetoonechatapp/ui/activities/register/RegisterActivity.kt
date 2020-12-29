package com.android.onetoonechatapp.ui.activities.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentTransaction
import com.android.onetoonechatapp.R
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
        mToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mToolbar)
    }

    private fun init() {

        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(
            R.id.frame_layout_content_register,
            RegisterFragment.newInstance(),
            RegisterFragment::class.java.simpleName
        )

        fragmentTransaction.commit()
    }
}
package com.android.onetoonechatapp.ui.activities.users

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.android.onetoonechatapp.R
import com.android.onetoonechatapp.core.logout.LogoutContract
import com.android.onetoonechatapp.core.logout.LogoutPresenter
import com.android.onetoonechatapp.ui.activities.login.LoginActivity
import com.google.android.material.tabs.TabLayout


class UserListActivity : AppCompatActivity(), LogoutContract.View {

    private var mToolbar: Toolbar? = null
    private var mTabLayoutUserListing: TabLayout? = null
    private var mViewPagerUserListing: ViewPager? = null
    private var mLogoutPresenter: LogoutPresenter? = null

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, UserListActivity::class.java)
            context.startActivity(intent)
        }

        fun startActivity(context: Context, flags: Int) {
            val intent = Intent(context, UserListActivity::class.java)
            intent.flags = flags
            context.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        bindViews()
        init()
    }

    private fun bindViews() {
        mToolbar = findViewById(R.id.toolbar)
        mTabLayoutUserListing = findViewById(R.id.tab_layout_user_listing)
        mViewPagerUserListing = findViewById(R.id.view_pager_user_listing)
        setSupportActionBar(mToolbar)
    }

    private fun init() {

        val userListingPagerAdapter = UserListingPagerAdapter(
            supportFragmentManager
        )

        mViewPagerUserListing?.adapter = userListingPagerAdapter

        mTabLayoutUserListing?.setupWithViewPager(mViewPagerUserListing)
        mLogoutPresenter = LogoutPresenter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_user_listing, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> logout()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout() {
        AlertDialog.Builder(this)
            .setTitle(R.string.logout)
            .setMessage(R.string.are_you_sure)
            .setPositiveButton(R.string.logout) { dialog, _ ->
                dialog.dismiss()
                mLogoutPresenter?.logout()

            }.setNegativeButton(
                R.string.cancel
            ) { dialog, _ ->
                dialog.dismiss()

            }.show()
    }

    override fun onLogoutSuccess(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        LoginActivity.startIntent(
            this, Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        )
    }

    override fun onLogoutFailure(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
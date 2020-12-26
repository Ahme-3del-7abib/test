package com.simplx.apps.chatapp2.ui.parent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.simplx.apps.chatapp2.R
import com.simplx.apps.chatapp2.ui.parent.ui.account.MyAccountFragment
import com.simplx.apps.chatapp2.ui.parent.ui.home.PeopleFragment
import kotlinx.android.synthetic.main.activity_parent.*

class ParentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent)

        replaceFragment(PeopleFragment())

        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_people -> {
                    replaceFragment(PeopleFragment())
                    true
                }
                R.id.navigation_my_account -> {
                    replaceFragment(MyAccountFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_layout, fragment)
            .commit()
    }
}
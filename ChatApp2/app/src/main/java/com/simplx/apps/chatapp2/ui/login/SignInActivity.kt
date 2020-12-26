package com.simplx.apps.chatapp2.ui.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.simplx.apps.chatapp2.R
import com.simplx.apps.chatapp2.ui.parent.ParentActivity
import com.simplx.apps.chatapp2.utils.FireStoreUtils
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class SignInActivity : AppCompatActivity() {

    private val signInProvider =
        listOf(
            AuthUI.IdpConfig.EmailBuilder()
                .setAllowNewAccounts(true)
                .setRequireName(true)
                .build()
        )

    private val RC_SIGN_IN = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        con_SingInBtn.setOnClickListener {
            val intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(signInProvider)
                .setLogo(R.mipmap.ic_launcher)
                .build()

            startActivityForResult(intent, RC_SIGN_IN)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {

            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {

                val progressDialog = indeterminateProgressDialog("Setting Up Your Account")
                FireStoreUtils.initCurrentUserIfFirstTime {
                    startActivity(intentFor<ParentActivity>().newTask().clearTask())
                    progressDialog.dismiss()
                }

            } else if (resultCode == Activity.RESULT_CANCELED) {

                if (response == null) {
                    return
                }

                when (response.error?.errorCode) {

                    ErrorCodes.NO_NETWORK -> {
                        Toast.makeText(this, "No Internet Connection.", Toast.LENGTH_SHORT).show()
                    }

                    ErrorCodes.UNKNOWN_ERROR -> {
                        Toast.makeText(this, "Un Known Error !!", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }
}
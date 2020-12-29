package com.android.onetoonechatapp.ui.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.onetoonechatapp.R
import com.android.onetoonechatapp.core.login.LoginContract
import com.android.onetoonechatapp.core.login.LoginPresenter
import com.android.onetoonechatapp.ui.activities.register.RegisterActivity
import com.android.onetoonechatapp.ui.activities.users.UserListActivity


class LoginFragment : Fragment(), View.OnClickListener, LoginContract.View {

    private var mLoginPresenter: LoginPresenter? = null

    private var mETxtEmail: EditText? = null
    private var mETxtPassword: EditText? = null
    private var mBtnLogin: Button? = null
    private var mBtnRegister: Button? = null

    private var mProgressDialog: ProgressDialog? = null

    companion object {
        fun newInstance(): LoginFragment {
            val args = Bundle()
            val fragment = LoginFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentView: View = inflater.inflate(R.layout.fragment_login, container, false)
        bindViews(fragmentView)
        return fragmentView
    }

    private fun bindViews(view: View) {
        mETxtEmail = view.findViewById(R.id.edit_text_email_id)
        mETxtPassword = view.findViewById(R.id.edit_text_password)
        mBtnLogin = view.findViewById(R.id.button_login)
        mBtnRegister = view.findViewById(R.id.button_register)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        mLoginPresenter = LoginPresenter(this)
        mProgressDialog = ProgressDialog(activity)
        mProgressDialog?.setTitle(getString(R.string.loading))
        mProgressDialog?.setMessage(getString(R.string.please_wait))
        mProgressDialog?.isIndeterminate = true
        mBtnLogin!!.setOnClickListener(this)
        mBtnRegister!!.setOnClickListener(this)
        setDummyCredentials()
    }

    private fun setDummyCredentials() {
        mETxtEmail?.setText("test@test.com")
        mETxtPassword?.setText("123456")
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_login -> onLogin()
            R.id.button_register -> onRegister()
        }
    }

    private fun onLogin() {
        val emailId = mETxtEmail?.text.toString()
        val password = mETxtPassword?.text.toString()
        mLoginPresenter?.login(activity, emailId, password)
        mProgressDialog?.show()
    }

    private fun onRegister() {
        activity?.let { RegisterActivity.startActivity(it) }
    }

    override fun onLoginSuccess(message: String?) {
        mProgressDialog?.dismiss()
        Toast.makeText(activity, "Logged in successfully", Toast.LENGTH_SHORT).show()
        UserListActivity.startActivity(
            requireContext(),
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        )
    }

    override fun onLoginFailure(message: String?) {
        mProgressDialog?.dismiss()
        Toast.makeText(activity, "Error: $message", Toast.LENGTH_SHORT).show();
    }
}
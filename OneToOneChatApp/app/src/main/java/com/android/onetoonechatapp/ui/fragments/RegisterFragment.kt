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
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import com.android.onetoonechatapp.R
import com.android.onetoonechatapp.core.registeration.RegisterContract
import com.android.onetoonechatapp.core.registeration.RegisterPresenter
import com.android.onetoonechatapp.core.users.add.AddUserContract
import com.android.onetoonechatapp.core.users.add.AddUserPresenter
import com.android.onetoonechatapp.ui.activities.users.UserListActivity
import com.google.firebase.auth.FirebaseUser


class RegisterFragment : Fragment(), View.OnClickListener, RegisterContract.View,
    AddUserContract.View {

    private val mToolbar: Toolbar? = null

    private var mRegisterPresenter: RegisterPresenter? = null
    private var mAddUserPresenter: AddUserPresenter? = null

    private var mETxtEmail: EditText? = null
    private var mETxtPassword: EditText? = null
    private var mBtnRegister: Button? = null

    private var mProgressDialog: ProgressDialog? = null

    companion object {
        fun newInstance(): RegisterFragment {
            val args = Bundle()
            val fragment = RegisterFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentView: View = inflater.inflate(R.layout.fragment_register, container, false)
        bindViews(fragmentView)
        return fragmentView
    }

    private fun bindViews(view: View) {
        mETxtEmail = view.findViewById(R.id.edit_text_email_id)
        mETxtPassword = view.findViewById(R.id.edit_text_password)
        mBtnRegister = view.findViewById(R.id.button_register)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        mRegisterPresenter = RegisterPresenter(this)
        mAddUserPresenter = AddUserPresenter(this)
        mProgressDialog = ProgressDialog(activity)
        mProgressDialog?.setTitle(getString(R.string.loading))
        mProgressDialog?.setMessage(getString(R.string.please_wait))
        mProgressDialog?.isIndeterminate = true
        mBtnRegister?.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_register -> onRegister()
        }
    }

    private fun onRegister() {
        val emailId = mETxtEmail?.text.toString()
        val password = mETxtPassword?.text.toString()
        mRegisterPresenter?.register(activity, emailId, password)
        mProgressDialog?.show()
    }

    override fun onRegistrationSuccess(firebaseUser: FirebaseUser?) {
        mProgressDialog?.setMessage(getString(R.string.adding_user_to_db))
        Toast.makeText(activity, "Registration Successful!", Toast.LENGTH_SHORT).show()
        mAddUserPresenter?.addUser(activity, firebaseUser)
    }


    override fun onRegistrationFailure(message: String?) {
        mProgressDialog?.dismiss()
        mProgressDialog?.setMessage(getString(R.string.please_wait))

        Toast.makeText(activity, "Registration failed!+\n$message", Toast.LENGTH_LONG).show()
    }

    override fun onAddUserSuccess(message: String?) {
        mProgressDialog?.dismiss()
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()

        UserListActivity.startActivity(
            requireContext(), Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        )
    }

    override fun onAddUserFailure(message: String?) {
        mProgressDialog?.dismiss()
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}
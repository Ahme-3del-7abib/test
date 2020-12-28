package com.android.onetoonechatapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.onetoonechatapp.R
import com.android.onetoonechatapp.utils.Constants

class ChatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intent = Intent()

        intent.extras!!.getString(Constants.ARG_RECEIVER)
        intent.extras!!.getString(Constants.ARG_RECEIVER_UID)
        intent.extras!!.getString(Constants.ARG_FIREBASE_TOKEN)
    }
}
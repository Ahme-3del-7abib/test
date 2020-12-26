package com.simplx.apps.chatapp2.ui.parent.ui.account

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide.with
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.with
import com.firebase.ui.auth.AuthUI
import com.simplx.apps.chatapp2.R
import com.simplx.apps.chatapp2.glide.FireMessageGlideModule
import com.simplx.apps.chatapp2.glide.GlideApp
import com.simplx.apps.chatapp2.ui.login.SignInActivity
import com.simplx.apps.chatapp2.utils.FireStoreUtils
import com.simplx.apps.chatapp2.utils.StorageUtils
import kotlinx.android.synthetic.main.fragment_my_account.*
import java.io.ByteArrayOutputStream


class MyAccountFragment : Fragment() {

    private val RC_SELECT_IMAGE = 2
    private lateinit var selectedImageBytes: ByteArray
    private var pictureJustChanged = false

    // -- private lateinit var myAccountViewModel: MyAccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // -- myAccountViewModel = ViewModelProvider(this).get(MyAccountViewModel::class.java)
        return inflater.inflate(R.layout.fragment_my_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView_profile_picture.setOnClickListener {
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
            }
            startActivityForResult(
                Intent.createChooser(intent, "Select Image"),
                RC_SELECT_IMAGE
            )
        }

        btn_save.setOnClickListener {
            if (::selectedImageBytes.isInitialized) {
                StorageUtils.uploadProfilePhoto(selectedImageBytes) { imagePath ->
                    FireStoreUtils.updateCurrentUser(
                        editText_name.text.toString(),
                        editText_bio.text.toString(), imagePath
                    )
                }
            } else {
                FireStoreUtils.updateCurrentUser(
                    editText_name.text.toString(),
                    editText_bio.text.toString(), null
                )
            }

            Toast.makeText(requireContext(), "Saving", Toast.LENGTH_SHORT).show()
        }

        btn_sign_out.setOnClickListener {
            AuthUI.getInstance()
                .signOut(requireContext())
                .addOnCanceledListener {
                    val intent = Intent(requireContext(), SignInActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
        }
    }

    override fun onStart() {
        super.onStart()
        FireStoreUtils.getCurrentUser { user ->
            if (this@MyAccountFragment.isVisible) {
                editText_name.setText(user.name)
                editText_bio.setText(user.bio)
                if (!pictureJustChanged && user.profilePicPath != null) {
                    GlideApp.with(this).load(StorageUtils.pathToReference(user.profilePicPath))
                        .placeholder(R.mipmap.ic_launcher)
                        .into(imageView_profile_picture)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SELECT_IMAGE && resultCode == Activity.RESULT_OK &&
            data != null && data.data != null
        ) {
            val selectedImagePath = data.data
            val selectedImageBmp = MediaStore.Images.Media
                .getBitmap(activity?.contentResolver, selectedImagePath)

            val outputStream = ByteArrayOutputStream()
            selectedImageBmp.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            selectedImageBytes = outputStream.toByteArray()

            GlideApp.with(this)
                .load(selectedImageBytes)
                .into(imageView_profile_picture)

            pictureJustChanged = true
        }
    }
}
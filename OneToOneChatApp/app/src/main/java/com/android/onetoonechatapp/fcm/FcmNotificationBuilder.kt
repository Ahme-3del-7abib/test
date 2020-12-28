package com.android.onetoonechatapp.fcm

import android.util.Log

import org.json.JSONException
import org.json.JSONObject

import java.io.IOException

import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response


class FcmNotificationBuilder() {

    val MEDIA_TYPE_JSON: MediaType? = "application/json; charset=utf-8".toMediaTypeOrNull()

    private val TAG = "FcmNotificationBuilder"
    private val SERVER_API_KEY = "YOUR_SERVER_API_KEY"
    private val CONTENT_TYPE = "Content-Type"
    private val APPLICATION_JSON = "application/json"
    private val AUTHORIZATION = "Authorization"
    private val AUTH_KEY = "key=$SERVER_API_KEY"
    private val FCM_URL = "https://fcm.googleapis.com/fcm/send"

    // json related keys
    private val KEY_TO = "to"
    private val KEY_NOTIFICATION = "notification"
    private val KEY_TITLE = "title"
    private val KEY_TEXT = "text"
    private val KEY_DATA = "data"
    private val KEY_USERNAME = "username"
    private val KEY_UID = "uid"
    private val KEY_FCM_TOKEN = "fcm_token"

    private var mTitle: String? = null
    private var mMessage: String? = null
    private var mUsername: String? = null
    private var mUid: String? = null
    private var mFirebaseToken: String? = null
    private var mReceiverFirebaseToken: String? = null


    companion object {
        fun initialize(): FcmNotificationBuilder {
            return FcmNotificationBuilder()
        }
    }

    fun title(title: String): FcmNotificationBuilder {
        mTitle = title
        return this
    }

    fun message(message: String): FcmNotificationBuilder {
        mMessage = message
        return this
    }

    fun username(username: String): FcmNotificationBuilder {
        mUsername = username
        return this
    }

    fun uid(uid: String): FcmNotificationBuilder {
        mUid = uid
        return this
    }

    fun firebaseToken(firebaseToken: String): FcmNotificationBuilder {
        mFirebaseToken = firebaseToken
        return this
    }

    fun receiverFirebaseToken(receiverFirebaseToken: String): FcmNotificationBuilder {
        mReceiverFirebaseToken = receiverFirebaseToken
        return this
    }

    fun send() {

        var requestBody: RequestBody? = null
        try {
            requestBody = getValidJsonBody().toString().toRequestBody(MEDIA_TYPE_JSON)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val request: Request = Request.Builder()
            .addHeader(CONTENT_TYPE, APPLICATION_JSON)
            .addHeader(AUTHORIZATION, AUTH_KEY)
            .url(FCM_URL)
            .post(requestBody!!)
            .build()

        val call: Call = OkHttpClient().newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "onGetAllUsersFailure: " + e.message);
            }

            override fun onResponse(call: Call, response: Response) {
                Log.e(TAG, "onResponse: " + response.body.toString());
            }

        })
    }


    private fun getValidJsonBody(): JSONObject {
        val jsonObjectBody = JSONObject()
        jsonObjectBody.put(KEY_TO, mReceiverFirebaseToken)
        val jsonObjectData = JSONObject()
        jsonObjectData.put(KEY_TITLE, mTitle)
        jsonObjectData.put(KEY_TEXT, mMessage)
        jsonObjectData.put(KEY_USERNAME, mUsername)
        jsonObjectData.put(KEY_UID, mUid)
        jsonObjectData.put(KEY_FCM_TOKEN, mFirebaseToken)
        jsonObjectBody.put(KEY_DATA, jsonObjectData)
        return jsonObjectBody
    }
}
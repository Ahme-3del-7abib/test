package com.android.onetoonechatapp.utils

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import com.android.onetoonechatapp.R

class NetworkConnectionUtil {

    val ERR_DIALOG_TITLE = "No internet connection detected !"

    private val ERR_DIALOG_MSG =
        "Looks like our application is not able to detect an active internet connection, " +
                "please check your device's network settings."

    private val ERR_DIALOG_POSITIVE_BTN = "Settings"
    private val ERR_DIALOG_NEGATIVE_BTN = "Dismiss"


    fun isConnectedToInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

    fun isConnectedToWifi(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null &&
                networkInfo.isConnectedOrConnecting && networkInfo.type == ConnectivityManager.TYPE_WIFI
    }

    fun isConnectedToMobileNetwork(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null &&
                networkInfo.isConnectedOrConnecting && networkInfo.type == ConnectivityManager.TYPE_MOBILE
    }

    fun showNoInternetAvailableErrorDialog(context: Context) {
        AlertDialog.Builder(context)
            .setTitle(ERR_DIALOG_TITLE)
            .setMessage(ERR_DIALOG_MSG)
            .setIcon(R.drawable.ic_error_24dp)
            .setPositiveButton(
                ERR_DIALOG_POSITIVE_BTN,
                DialogInterface.OnClickListener { dialogInterface, _ ->
                    dialogInterface.dismiss()
                    val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
                    context.startActivity(intent)
                })

            .setNegativeButton(
                ERR_DIALOG_NEGATIVE_BTN,
                DialogInterface.OnClickListener { dialogInterface, _ -> dialogInterface.dismiss() })
            .show()
    }

}
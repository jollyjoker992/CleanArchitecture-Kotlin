package com.hieupham.absolutecleanarchitecturekt.feature

import android.support.v4.app.FragmentActivity
import android.support.v7.app.AlertDialog

class DialogManager(private val activity: FragmentActivity) {

    fun showError(throwable: Throwable) {
        AlertDialog.Builder(activity).setTitle("Error")
                .setMessage(throwable.message)
                .setCancelable(false)
                .setPositiveButton("OK"
                ) { dialog, _ -> dialog.dismiss() }
                .show()
    }
}
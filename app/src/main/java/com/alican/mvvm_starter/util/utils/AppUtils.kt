
package com.alican.mvvm_starter.util.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openAppOrPlayStore(packageName: String) {
    val appIntent = packageManager.getLaunchIntentForPackage(packageName)

    if (appIntent != null) {
        startActivity(appIntent)
    } else {
        val playStoreIntent = Intent(Intent.ACTION_VIEW)
        playStoreIntent.data = Uri.parse("market://details?id=$packageName")
        startActivity(playStoreIntent)
    }
}



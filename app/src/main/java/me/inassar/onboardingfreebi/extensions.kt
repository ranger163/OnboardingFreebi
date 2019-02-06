package me.inassar.onboardingfreebi

import android.content.Context
import android.os.Handler
import android.view.View
import android.widget.Toast

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun postDelayed(delayMillis: Long, task: () -> Unit) {
    Handler().postDelayed(task, delayMillis)
}
package me.inassar.onboardingfreebi.view.activity.login

import me.inassar.onboardingfreebi.postDelayed

class LoginInteractor {

    interface OnLoginFinishedListener {
        fun onUsernameError()
        fun onPasswordError()
        fun onSuccess()
    }

    fun login(username: String, password: String, listener: OnLoginFinishedListener) {

        // Mocking login
        postDelayed(2000) {
            when {
                username.isEmpty() -> listener.onUsernameError()
                password.isEmpty() -> listener.onPasswordError()
                else -> listener.onSuccess()
            }
        }
    }

}
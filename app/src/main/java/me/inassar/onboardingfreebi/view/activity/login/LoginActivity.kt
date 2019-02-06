package me.inassar.onboardingfreebi.view.activity.login

import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.*
import me.inassar.onboardingfreebi.R
import me.inassar.onboardingfreebi.config.AppPrefs
import me.inassar.onboardingfreebi.hide
import me.inassar.onboardingfreebi.show
import me.inassar.onboardingfreebi.state.LoginState
import me.inassar.onboardingfreebi.state.ScreenState
import me.inassar.onboardingfreebi.toolbar
import me.inassar.onboardingfreebi.view.activity.HomeActivity
import me.inassar.onboardingfreebi.view.activity.OnBoardingActivity
import me.inassar.onboardingfreebi.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Check if the app is launched before
        if (AppPrefs(this).isFirstTimeLaunch()) {
            startActivity(Intent(this, OnBoardingActivity::class.java))
            finish()
        }
        setContentView(R.layout.activity_login)

        init()
        interactions()
    }

    private fun init() {
        toolbar(toolBar)
        viewModel = ViewModelProviders.of(
                this,
                LoginViewModel.LoginViewModelFactory(LoginInteractor())
        )[LoginViewModel::class.java]
        viewModel.loginState.observe(::getLifecycle, ::updateUi)

        signUpTv.apply {
            text = Html.fromHtml("$text <font color='#6c63ff'>SignUp</font>")
        }
    }

    private fun interactions() {
        loginBtn.setOnClickListener {
            viewModel.onLoginBtnClicked(
                    usernameEt.editText?.text.toString(),
                    passwordEt.editText?.text.toString()
            )
        }
    }

    private fun updateUi(screenState: ScreenState<LoginState>?) {
        when (screenState) {
            ScreenState.Loading -> loading.show()
            is ScreenState.Render -> processLoginState(screenState.renderState)
        }
    }

    private fun processLoginState(renderState: LoginState) {
        loading.hide()
        when (renderState) {
            LoginState.SUCCESS -> {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
            LoginState.WRONG_USERNAME ->
                usernameEt.error = resources.getString(R.string.invalid_username)
            LoginState.WRONG_PASSWORD ->
                passwordEt.error = resources.getString(R.string.invalid_password)
        }
    }
}

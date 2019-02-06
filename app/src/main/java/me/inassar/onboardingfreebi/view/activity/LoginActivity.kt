package me.inassar.onboardingfreebi.view.activity

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import me.inassar.onboardingfreebi.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signUpTv.apply {
            text = Html.fromHtml("$text <font color='#6c63ff'>SignUp</font>")
        }
    }
}

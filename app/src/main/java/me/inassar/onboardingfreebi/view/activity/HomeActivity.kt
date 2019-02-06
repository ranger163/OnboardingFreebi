package me.inassar.onboardingfreebi.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.toolbar.*
import me.inassar.onboardingfreebi.R
import me.inassar.onboardingfreebi.toolbar

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        toolbar(toolBar, resources.getString(R.string.home_screen_title))
    }
}

package me.inassar.onboardingfreebi.view.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import kotlinx.android.synthetic.main.activity_onboarding.*
import me.inassar.onboardingfreebi.R
import me.inassar.onboardingfreebi.config.AppPrefs
import me.inassar.onboardingfreebi.hide
import me.inassar.onboardingfreebi.show
import me.inassar.onboardingfreebi.toast
import me.inassar.onboardingfreebi.view.adapter.SliderAdapter

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var sliderAdapter: SliderAdapter
    private var dots: Array<TextView?>? = null
    private lateinit var layouts: Array<Int>
    private lateinit var appPrefs: AppPrefs

    private val sliderChangeListener = object : OnPageChangeListener {

        override fun onPageSelected(position: Int) {
            addBottomDots(position)

            if (position == layouts.size.minus(1)) {
                nextBtn.hide()
                skipBtn.hide()
                startBtn.show()
            } else {
                nextBtn.show()
                skipBtn.show()
                startBtn.hide()
            }
        }

        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Make notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN and
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }

        setContentView(R.layout.activity_onboarding)

        init()
        dataSet()
        interactions()
    }

    private fun init() {
        appPrefs = AppPrefs(this)
        if (!appPrefs.isFirstTimeLaunch()) {
            // Launch login screen
            startActivity(Intent(this, LoginActivity::class.java))
        }

        /** Layouts of the three onBoarding Screens.
         *  Add more layouts if you wish.
         **/
        layouts = arrayOf(
                R.layout.onboarding_slide1,
                R.layout.onboarding_slide2,
                R.layout.onboarding_slide3
        )

        sliderAdapter = SliderAdapter(this, layouts)
    }

    private fun dataSet() {
        /**
         * Adding bottom dots
         * */
        addBottomDots(0)

        slider.apply {
            adapter = sliderAdapter
            addOnPageChangeListener(sliderChangeListener)
        }
    }

    private fun interactions() {
        skipBtn.setOnClickListener {
            // Launch login screen
            toast("Go to login")
        }
        startBtn.setOnClickListener {
            // Launch login screen
            appPrefs.setFirstTimeLaunch(false)
            toast("Go to login")
        }
        nextBtn.setOnClickListener {
            /**
             *  Checking for last page, if last page
             *  login screen will be launched
             * */
            val current = getCurrentScreen(+1)
            if (current < layouts.size) {
                /**
                 * Move to next screen
                 * */
                slider.currentItem = current
            } else {
                // Launch login screen
                toast("Go to login")
            }
        }
    }

    private fun addBottomDots(currentPage: Int) {
        dots = arrayOfNulls(layouts.size)

        dotsLayout.removeAllViews()
        for (i in 0 until dots!!.size) {
            dots!![i] = TextView(this)
            dots!![i]?.text = Html.fromHtml("&#8226;")
            dots!![i]?.textSize = 35f
            dots!![i]?.setTextColor(resources.getColor(R.color.colorGrey))
            dotsLayout.addView(dots!![i])
        }

        if (dots!!.isNotEmpty()) {
            dots!![currentPage]?.setTextColor(resources.getColor(R.color.colorIndigo))
        }
    }

    private fun getCurrentScreen(i: Int): Int = slider.currentItem.plus(i)

}

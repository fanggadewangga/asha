package com.nyautech.asha

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import com.nyautech.asha.adapter.ViewPagerAdapter
import com.nyautech.asha.databinding.ActivityOnboardingBinding
import com.nyautech.asha.sign.SignInActivity
import com.nyautech.asha.sign.SignUpActivity
import java.time.format.SignStyle

class OnboardingActivity : AppCompatActivity() {

    private var titleList = mutableListOf<String>()
    private var detailList = mutableListOf<String>()
    private var imagesList = mutableListOf<Int>()
    private lateinit var binding : ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        addToList("Onboarding 1","Detail 1",R.drawable.onboarding1)
        addToList("Onboarding 2","Detail 2",R.drawable.onboarding2)
        addToList("Onboarding 3","Detail 3",R.drawable.onboarding3)

        binding.vpOnboarding.apply {
            adapter = ViewPagerAdapter(titleList,detailList,imagesList)
            orientation = ORIENTATION_HORIZONTAL
        }

        binding.indicator.setViewPager(binding.vpOnboarding)


        // clicl
        binding.btnSignup.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }

    }

    private fun addToList(title : String,detail : String,image:Int){
        titleList.add(title)
        detailList.add(detail)
        imagesList.add(image)
    }
}
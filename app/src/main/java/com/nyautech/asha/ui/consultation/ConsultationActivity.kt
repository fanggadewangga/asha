package com.nyautech.asha.ui.consultation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nyautech.asha.ui.home.HomeActivity
import com.nyautech.asha.R
import com.nyautech.asha.databinding.ActivityConsultationBinding
import com.nyautech.asha.ui.article.ExploreActivity

private lateinit var binding : ActivityConsultationBinding
class ConsultationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityConsultationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bottomNavigation.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.nav_article -> startActivity(Intent(this, ExploreActivity::class.java))
                R.id.nav_home -> startActivity(Intent(this, HomeActivity::class.java))
                R.id.nav_concultation -> startActivity(Intent(this,ConsultationActivity::class.java))
            }
        }
    }
}
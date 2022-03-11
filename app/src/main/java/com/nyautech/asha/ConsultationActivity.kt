package com.nyautech.asha

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nyautech.asha.databinding.ActivityConsultationBinding

private lateinit var binding : ActivityConsultationBinding
class ConsultationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityConsultationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bottomNavigation.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.nav_article -> startActivity(Intent(this,ExploreActivity::class.java))
                R.id.nav_home -> startActivity(Intent(this,HomeActivity::class.java))
                R.id.nav_concultation -> startActivity(Intent(this,ConsultationActivity::class.java))
            }
        }
    }
}
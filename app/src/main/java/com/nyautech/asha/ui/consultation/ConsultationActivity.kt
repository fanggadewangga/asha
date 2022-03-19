package com.nyautech.asha.ui.consultation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nyautech.asha.ui.home.HomeActivity
import com.nyautech.asha.R
import com.nyautech.asha.adapter.ExpertChategoryAdapter
import com.nyautech.asha.databinding.ActivityConsultationBinding
import com.nyautech.asha.ui.article.ExploreActivity


private lateinit var binding : ActivityConsultationBinding

@Suppress("DEPRECATION")

class ConsultationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityConsultationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        // nav
        binding.icArticle.setOnClickListener {
            startActivity(Intent(this,ExploreActivity::class.java))
        }
        binding.icHome.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
        }



    }

}
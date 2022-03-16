package com.nyautech.asha.ui.article

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nyautech.asha.R
import com.nyautech.asha.adapter.ArticleAdapter
import com.nyautech.asha.data.DataArticle
import com.nyautech.asha.databinding.ActivityExploreBinding
import com.nyautech.asha.ui.consultation.ConsultationActivity
import com.nyautech.asha.ui.home.HomeActivity

private lateinit var binding : ActivityExploreBinding

class ExploreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityExploreBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val articleAdapter = ArticleAdapter()
        articleAdapter.saveAllData(DataArticle.listOfArticle(this))

        binding.rvExploreArticle.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(this@ExploreActivity,LinearLayoutManager.VERTICAL,false)
        }

        binding.bottomNavigation.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.nav_article -> startActivity(Intent(this,ExploreActivity::class.java))
                R.id.nav_home -> startActivity(Intent(this, HomeActivity::class.java))
                R.id.nav_concultation -> startActivity(Intent(this, ConsultationActivity::class.java))
            }
        }
    }
}
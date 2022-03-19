package com.nyautech.asha.ui.article

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nyautech.asha.R
import com.nyautech.asha.adapter.ArticleAdapter
import com.nyautech.asha.data.DataArticle
import com.nyautech.asha.databinding.ActivityExploreBinding
import com.nyautech.asha.ui.consultation.ConsultationActivity
import com.nyautech.asha.ui.home.HomeActivity

private lateinit var binding : ActivityExploreBinding

@Suppress("DEPRECATION")
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

        binding.svExplore.apply {
            queryHint = resources.getString(R.string.article_text)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    val query = query.toString()
                    clearFocus()
                    val dataArticle = DataArticle
                    dataArticle.searchArticle(query, this@ExploreActivity)
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }



        // nav
        binding.icHome.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
        }
        binding.icChat.setOnClickListener {
            startActivity(Intent(this,ConsultationActivity::class.java))
        }
    }
}
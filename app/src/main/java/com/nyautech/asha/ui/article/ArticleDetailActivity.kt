package com.nyautech.asha.ui.article

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nyautech.asha.R
import com.nyautech.asha.databinding.ActivityArticleDetailBinding
import com.nyautech.asha.model.Article
import com.nyautech.asha.ui.consultation.ConsultationActivity
import com.nyautech.asha.util.Constanta.EXTRA_ARTICLE

private lateinit var binding : ActivityArticleDetailBinding
class ArticleDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        // get article detail
        val article = intent.getParcelableExtra<Article>(EXTRA_ARTICLE)
        binding.apply {
            tvArticleTitle.text = article?.title
            tvArticleDate.text = article?.releaseDate
            tvAuthorName.text = article?.author
            tvArticleText.text = article?.articleDetail
            ivArticleImage.setImageResource(article?.articleBackground ?: 0)
        }
        Glide.with(this)
            .load(article?.authorImage)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.ivAuthorImg)
    }
}
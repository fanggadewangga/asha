package com.nyautech.asha.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nyautech.asha.databinding.ItemListArticleBinding
import com.nyautech.asha.model.Article
import com.nyautech.asha.ui.article.ArticleDetailActivity
import com.nyautech.asha.util.Constanta.EXTRA_ARTICLE

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    val listArticle = ArrayList<Article>()

    fun saveAllData(data : List<Article>){
        listArticle.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = ItemListArticleBinding.inflate(LayoutInflater.from(parent.context),parent ,false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(listArticle[position])
    }

    override fun getItemCount(): Int = listArticle.size

    inner class ArticleViewHolder(private val view : ItemListArticleBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(article: Article){
            view.apply {
                view.tvListArticleTitle.text = article.title
                view.tvListArticleAuthor.text = article.author
                view.tvListArticleDate.text = article.releaseDate
                view.ivArticleImage.setImageResource(article.articleBackground)
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context,ArticleDetailActivity::class.java)
                intent.putExtra(EXTRA_ARTICLE,article)
                itemView.context.startActivity(intent)
            }
        }

    }
}
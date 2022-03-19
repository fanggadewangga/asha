package com.nyautech.asha.data

import android.content.Context
import android.content.res.TypedArray
import com.nyautech.asha.R
import com.nyautech.asha.model.Article

object DataArticle {
    private lateinit var listOfTitle : Array<String>
    private lateinit var listOfReleaseDate : Array<String>
    private lateinit var listOfAuthor : Array<String>
    private lateinit var listOfAuthorImage : TypedArray
    private lateinit var listOfArticleDetail : Array<String>
    private lateinit var listOfArticleBackground : TypedArray

    private fun prepareData(context: Context){
        listOfTitle = context.resources.getStringArray(R.array.article_title)
        listOfReleaseDate = context.resources.getStringArray(R.array.article_date)
        listOfAuthor = context.resources.getStringArray(R.array.article_author_name)
        listOfAuthorImage = context.resources.obtainTypedArray(R.array.article_author_img)
        listOfArticleDetail = context.resources.getStringArray(R.array.article_detail)
        listOfArticleBackground = context.resources.obtainTypedArray(R.array.article_background)
    }

    fun listOfArticle(context: Context) : ArrayList<Article>{

        val listOfArticle = ArrayList<Article>()
        prepareData(context)

        for (position in listOfTitle.indices){
            val article = Article(
                title = listOfTitle[position],
                releaseDate = listOfReleaseDate[position],
                author = listOfAuthor[position],
                authorImage = listOfAuthorImage.getResourceId(position,0),
                articleDetail = listOfArticleDetail[position],
                articleBackground = listOfArticleBackground.getResourceId(position,0)
            )
            listOfArticle.add(article)
        }

        return listOfArticle
    }

    fun searchArticle(query : String, context: Context) : ArrayList<Article>{
        var searchResult = ArrayList<Article>()

        listOfArticle(context).forEach {
            if (it.title.contains(query)){
                searchResult.add(it)
            }
        }

        return searchResult
    }
}
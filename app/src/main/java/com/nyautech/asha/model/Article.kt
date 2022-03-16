package com.nyautech.asha.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(val title : String,
                   val releaseDate : String,
                   val author : String,
                   val authorImage : Int,
                   val articleDetail : String,
                   val articleBackground : Int) : Parcelable

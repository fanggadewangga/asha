package com.nyautech.asha.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nyautech.asha.R

class ViewPagerAdapter(private val title : List<String>,
                       private val detail : List<String>,
                       private val image : List<Int> ) : RecyclerView.Adapter<ViewPagerAdapter.Pager2ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Pager2ViewHolder {
        return Pager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_onboarding,parent,false))
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        holder.itemTitle.text = title[position]
        holder.itemDetail.text = detail[position]
        holder.itemImage.setImageResource(image[position])
    }

    override fun getItemCount(): Int {
        return title.size
    }

    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle : TextView = itemView.findViewById(R.id.tv_onboarding_title)
        val itemDetail : TextView = itemView.findViewById(R.id.tv_onboarding_detail)
        val itemImage : ImageView = itemView.findViewById(R.id.iv_onboarding_image)
    }

}
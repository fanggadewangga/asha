package com.nyautech.asha.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nyautech.asha.R

class ExpertChategoryAdapter(
    private val chategoryTitle : List<String>,
    private val chategoryImage : List<Int>
) : RecyclerView.Adapter<ExpertChategoryAdapter.ExpertChategoryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpertChategoryViewHolder {
        return ExpertChategoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_category,parent,false))
    }

    override fun onBindViewHolder(holder: ExpertChategoryViewHolder, position: Int) {
        holder.chategoryTitle.text = chategoryTitle[position]
        holder.chategoryIcon.setImageResource(chategoryImage[position])
    }

    override fun getItemCount(): Int {
        return chategoryTitle.size
    }

    inner class ExpertChategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chategoryTitle : TextView = itemView.findViewById(R.id.tv_expert_chategory)
        val chategoryIcon : ImageView = itemView.findViewById(R.id.iv_expert_category)
    }
}
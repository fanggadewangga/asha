package com.nyautech.asha.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nyautech.asha.R
import com.nyautech.asha.model.Expert
import com.nyautech.asha.model.User

class ConsultationAdapter(val context: Context, val expertList : ArrayList<User>): RecyclerView.Adapter<ConsultationAdapter.ConsultationViewHolder>() {

    override fun onCreateViewHolder(
       parent: ViewGroup,viewType: Int): ConsultationAdapter.ConsultationViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_list_chat,parent,false)
        return ConsultationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConsultationAdapter.ConsultationViewHolder, position: Int) {
        val currentExpert = expertList[position]

        holder.expertName.text = currentExpert.name
        holder.expertCategory.text = currentExpert.username
    }

    override fun getItemCount(): Int {
        return expertList.size
    }

    inner class ConsultationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expertName : TextView = itemView.findViewById(R.id.tv_chat_name)
        val expertCategory : TextView = itemView.findViewById(R.id.tv_chat_category)
    }
}

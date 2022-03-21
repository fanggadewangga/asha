package com.nyautech.asha.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ValueEventListener
import com.nyautech.asha.R
import com.nyautech.asha.model.Expert
import com.nyautech.asha.model.User
import com.nyautech.asha.ui.chat.ChatActivity

class ConsultationAdapter(val context: Context, private val expertList: ArrayList<Expert>): RecyclerView.Adapter<ConsultationAdapter.ConsultationViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,viewType: Int): ConsultationAdapter.ConsultationViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_list_chat,parent,false)
        return ConsultationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConsultationAdapter.ConsultationViewHolder, position: Int) {
        val currentExpert = expertList[position]

        holder.expertName.text = currentExpert.username
        holder.expertCategory.text = currentExpert.category

        holder.itemView.setOnClickListener {
            val intent = Intent(context,ChatActivity::class.java)
            intent.putExtra("name",currentExpert.name)
            intent.putExtra("category",currentExpert.category)
            intent.putExtra("eid",currentExpert.eid)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return expertList.size
    }

    inner class ConsultationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expertName : TextView = itemView.findViewById(R.id.tv_chat_name)
        val expertCategory : TextView = itemView.findViewById(R.id.tv_chat_category)
    }
}
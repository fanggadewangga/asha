package com.nyautech.asha.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.nyautech.asha.R

class MessageAdapter(val context: Context, private val messageList: ArrayList<com.nyautech.asha.model.Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_RECEIVE = 1
    private val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == 1){
            // inflate item message receive
            val view = LayoutInflater.from(context).inflate(R.layout.item_message_received,parent,false)
            ReceiveViewHolder(view)
        }else{
            // inflate item message sent
            val view = LayoutInflater.from(context).inflate(R.layout.item_message_sent,parent,false)
            SentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder.javaClass == SentViewHolder::class.java){
            // do the stuff for sent view holder
            val currentMessage = messageList[position]
            val viewHolder = holder as SentViewHolder

            // set message
            holder.sentMessage.text = currentMessage.message

        }else{
            // do the stuff for receive view holder
            val currentMessage = messageList[position]
            val viewHolder = holder as ReceiveViewHolder

            // set message
            holder.receiveMessage.text = currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {

        val currentMessage = messageList[position]

        return if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderID)){
            ITEM_SENT
        }else{
            ITEM_RECEIVE
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }


    class SentViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val sentMessage: TextView = itemView.findViewById(R.id.tv_message_sent)
    }

    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val receiveMessage: TextView = itemView.findViewById(R.id.tv_message_received)
    }

}
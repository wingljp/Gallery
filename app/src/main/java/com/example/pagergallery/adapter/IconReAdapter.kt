package com.example.pagergallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pagergallery.IconListActivity
import com.example.pagergallery.R
import com.example.pagergallery.modes.Resultd

class IconReAdapter() :
    PagingDataAdapter<Resultd, IconReAdapter.MyViewHolder>(DIFFCALLBACK) {


    object DIFFCALLBACK : DiffUtil.ItemCallback<Resultd>() {




        override fun areItemsTheSame(oldItem: Resultd, newItem: Resultd): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Resultd, newItem: Resultd): Boolean {
            return oldItem.name == newItem.name&&oldItem.species==newItem.species
        }


    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position)!!,position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.icon_list_item, parent, false)

        return MyViewHolder(inflater)
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageView = itemView.findViewById<ImageView>(R.id.icon)
        val textView = itemView.findViewById<TextView>(R.id.tv_name)
        fun bind(data: Resultd, position: Int) {

            textView.text = data.name
            Glide.with(imageView).load(data.image).into(imageView)
        }


    }


}
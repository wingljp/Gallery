package com.example.pagergallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pagergallery.R
import com.example.pagergallery.modes.PhotoItem
import kotlinx.android.synthetic.main.fragment_pager_photo.view.*
import kotlinx.android.synthetic.main.pager_photo_item.view.*

class PagerOhotoListAdapter :
    ListAdapter<PhotoItem, PagerOhotoListAdapter.PagePhotoHodler>(DiffCallBack) {

    object DiffCallBack : DiffUtil.ItemCallback<PhotoItem>() {
        override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem.id === newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagePhotoHodler {
        LayoutInflater.from(parent.context).inflate(R.layout.pager_photo_item, parent, false)
            .apply {

                return PagePhotoHodler(this)
            }
    }

    override fun onBindViewHolder(holder: PagePhotoHodler, position: Int) {
        Glide.with(holder.itemView).load(getItem(position).preview).placeholder(R.drawable.ic_launcher_foreground).into(holder.itemView.photoView)
    }

    class PagePhotoHodler(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }


}
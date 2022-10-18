package com.example.gallery

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.gallery.modes.PhotoItem
import kotlinx.android.synthetic.main.re_item.view.*


class GalleryAdapter : ListAdapter<PhotoItem, GalleryAdapter.MyViewHodler>(DIFFCALLBACK) {


    object DIFFCALLBACK : DiffUtil.ItemCallback<PhotoItem>() {
        override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem.id === newItem.id
        }


    }

    class MyViewHodler(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHodler {
        val hodler = MyViewHodler(
            LayoutInflater.from(parent.context).inflate(R.layout.re_item, parent, false)
        )


        hodler.itemView.setOnClickListener {

            Bundle().apply {
                putParcelable("datas", getItem(hodler.adapterPosition))
                hodler.itemView.findNavController().navigate(R.id.action_gralleryFragment_to_photoFragment,this)
            }

        }

        return hodler
    }

    override fun onBindViewHolder(holder: MyViewHodler, position: Int) {
        holder.itemView.shimmer.run {
            setShimmerColor(0x55FFF000)
            setShimmerAngle(1)
            startShimmerAnimation()
        }
        Glide.with(holder.itemView).load(getItem(position).preview)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false.also { holder.itemView.shimmer?.stopShimmerAnimation() }
                }


            })
            .placeholder(R.drawable.ic_launcher_foreground).into(holder.itemView.imageView)
    }
}
package com.example.pagergallery.adapter

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.pagergallery.R
import com.example.pagergallery.fragments.CAN_LOAD
import com.example.pagergallery.fragments.GalleryViewModel
import com.example.pagergallery.fragments.IS_DONE
import com.example.pagergallery.fragments.NET_ERRE
import com.example.pagergallery.modes.PhotoItem
import kotlinx.android.synthetic.main.gallery_item.view.*
import kotlinx.android.synthetic.main.grellery_footer.view.*


class GalleryAdapter(val gviewode: GalleryViewModel) :
    ListAdapter<PhotoItem, GalleryAdapter.MyViewHodler>(DIFFCALLBACK) {

    companion object {
        const val NORMAL_VIEW_TYPE = 0
        const val FOOTER_VIEW_TYOE = 1
    }

    var footerViewStatue = CAN_LOAD

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

        val holder: MyViewHodler

        if (viewType == NORMAL_VIEW_TYPE) {
            holder = MyViewHodler(
                LayoutInflater.from(parent.context).inflate(R.layout.gallery_item, parent, false)
            )

            holder.itemView.setOnClickListener {

                Bundle().apply {
                    putInt("position", holder.adapterPosition)
                    putParcelableArrayList("photo_list", ArrayList(currentList))
                    holder.itemView.findNavController()
                        .navigate(R.id.action_gralleryFragment_to_pagerPhotoFragment, this)
                }

            }
        } else {
            holder = MyViewHodler(
                LayoutInflater.from(parent.context).inflate(R.layout.grellery_footer, parent, false)
            ).also {
                (it.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams).isFullSpan =
                    true
                it.itemView.setOnClickListener { itemView ->
                    itemView.progressBar.visibility = View.VISIBLE
                    itemView.textView.text = "正在加载"
                    gviewode.fetchData()
                }


            }
        }

        return holder
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) FOOTER_VIEW_TYOE else NORMAL_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }

    override fun onBindViewHolder(holder: MyViewHodler, position: Int) {
        if (position == itemCount - 1) {
            with(holder.itemView) {
                when (footerViewStatue) {

                    CAN_LOAD -> {

                        this.progressBar.visibility = View.VISIBLE
                        textView.text = "正在加载"
                        isClickable = false
                    }
                    IS_DONE -> {

                        this.progressBar.visibility = View.GONE
                        textView.text = "全部加载完成"
                        isClickable = false
                    }
                    NET_ERRE -> {

                        this.progressBar.visibility = View.GONE
                        textView.text = "网络错误,请重试"
                        isClickable = true
                    }


                    else -> {}
                }
            }

            return

        }
        LoadDatas(holder, position)
    }

    private fun GalleryAdapter.LoadDatas(
        holder: MyViewHodler, position: Int
    ) {
        with(holder.itemView) {
            shimmerLayoutCell.apply {
                setShimmerColor(0x55FFF000)
                setShimmerAngle(1)
                startShimmerAnimation()
            }
            imageView.layoutParams.height = getItem(position).photoHeight

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
                    return false.also { holder.itemView.shimmerLayoutCell?.stopShimmerAnimation() }
                }


            }).placeholder(R.drawable.ic_launcher_foreground).into(holder.itemView.imageView)
    }
}
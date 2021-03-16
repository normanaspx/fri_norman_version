package com.normanaspx.norman_fri.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.normanaspx.norman_fri.R
import com.normanaspx.norman_fri.data.Photo
import com.normanaspx.norman_fri.databinding.ItemPhotoBinding


/**
Creada por Norman el 3/14/2021
 **/
class UnsplashPhotoAdapter(private val listener: OnItemClickListener):  PagingDataAdapter<Photo, UnsplashPhotoAdapter.PhotoViewHolder>(PHOTO_COMPARATOR) {

    private var photos: PagingData<Photo> = PagingData.empty()
    private var ls: MutableList<String> = mutableListOf()


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
            val binding =
                ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            return PhotoViewHolder(binding)
        }


        override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
            val currentItem = getItem(position)

            if (currentItem != null) {
                holder.bind(currentItem)
            }
        }

    inner  class PhotoViewHolder(private val binding: ItemPhotoBinding) :
            RecyclerView.ViewHolder(binding.root) {
            init {

                binding.root.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = getItem(position)
                        if (item != null) {
                            listener.onItemClick(item)
                        }
                    }
                }
            }
            fun bind(photo: Photo) {
                binding.apply {
                    Glide.with(itemView)
                        .load(photo.urls.regular)
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .error(R.drawable.ic_favorite_border_24px)
                        .into(imgCard)

                    username.text = photo.user.username
                    txtLikes.text = photo.likes.toString()
                }
            }
        }
        interface OnItemClickListener {
            fun onItemClick(photo: Photo)
        }

        companion object {
            private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<Photo>() {
                override fun areItemsTheSame(oldItem: Photo, newItem: Photo) =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Photo, newItem: Photo) =
                    oldItem == newItem
            }
        }

}


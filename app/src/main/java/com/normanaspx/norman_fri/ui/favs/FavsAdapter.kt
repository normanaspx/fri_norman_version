package com.normanaspx.norman_fri.ui.favs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.normanaspx.norman_fri.R
import com.normanaspx.norman_fri.data.Photo
import com.normanaspx.norman_fri.databinding.ItemPhotoBinding
import java.util.*
import kotlin.collections.ArrayList


/**
Creada por Norman el 3/14/2021
 **/
class FavsAdapter(
    private val listener: OnItemClickListener,
    private var userList: ArrayList<Photo>
):  RecyclerView.Adapter<FavsAdapter.UserViewHolder>(), Filterable{

    var filterList: ArrayList<Photo> = ArrayList()
    lateinit var context: Context
    init {
        filterList = userList
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        context = parent.context;

        val binding =
            ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }




    override fun getItemCount(): Int = filterList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentItem = filterList[position]

      //  if (currentItem != null) {
            holder.bind(currentItem)
       // }
        val user=userList[position]
      //  holder.name.text=user.user.username


    }

    inner class UserViewHolder(private val binding: ItemPhotoBinding)
        : RecyclerView.ViewHolder(binding.root){
        val name: TextView =itemView.findViewById(R.id.username)
        val img: ImageView =itemView.findViewById(R.id.imgCard)

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
                    .load(photo.urls.image)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_favorite_border_24px)
                    .into(imgCard)

                username.text = "@"+photo.user.name
                txtLikes.text = photo.likes.toString()
            }
        }


    }

    interface OnItemClickListener {
        fun onItemClick(photo: Photo)
    }

    fun getItem(position: Int): Photo? {
        return filterList[position]
    }

    fun setUser(userList: ArrayList<Photo>){
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filterList = userList
                } else {
                    val resultList = ArrayList<Photo>()
                    for (row in userList) {
                        if (row.user.name.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    filterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterList = results?.values as ArrayList<Photo>
                notifyDataSetChanged()
            }

        }
    }


}


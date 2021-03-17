package com.normanaspx.norman_fri.ui.favs

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.normanaspx.norman_fri.R
import com.normanaspx.norman_fri.data.Photo
import com.normanaspx.norman_fri.data.models.PhotoEntity
import com.normanaspx.norman_fri.data.models.PhotoWithDetails
import com.normanaspx.norman_fri.data.models.UrlsEntity
import com.normanaspx.norman_fri.data.models.UserEntity
import com.normanaspx.norman_fri.databinding.FragmentDetailsBinding
import com.normanaspx.norman_fri.ui.gallery.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_details.*


/**
Creada por Norman el 3/15/2021
 **/
@AndroidEntryPoint
class DetailsFavsFragment : Fragment(R.layout.fragment_details_favs) {

    private val args by navArgs<DetailsFavsFragmentArgs>()
    private val userViewModel: GalleryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailsBinding.bind(view)

        binding.apply {
            val photo = args.Photo

            Glide.with(this@DetailsFavsFragment)
                .load(photo.urls.full)
                .error(R.drawable.ic_error)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        textViewName.isVisible = true
                        textViewBio.isVisible = true
                        textViewDesc.isVisible = photo.description != null
                        return false
                    }
                })
                .into(imageView)

            photo.like = true;
            checkLike(photo)

            textViewDesc.text = photo.description
            textViewUsername.apply {
                text = "@${photo.user.username}"
                setTypeface(null, Typeface.BOLD)
            }

            imgLike.setOnClickListener {


                val photoDetail: PhotoWithDetails = PhotoWithDetails(
                    PhotoEntity(photo.id, photo.description, photo.likes, photo.like),
                    UrlsEntity(photo.urls.raw, photo.urls.full, photo.urls.regular, photo.id),
                    UserEntity(photo.user.id, photo.user.name, photo.user.username, photo.user.bio, photo.id)
                )

                userViewModel.insert(photoDetail)
                checkLike(photo)
            }
            //user
            textViewName.text=photo.user.name
            textViewBio.text=photo.user.bio
        }
    }


    fun checkLike(photo: Photo){
        if(photo.like){
            photo.like = false
            Glide.with(this@DetailsFavsFragment)
                .load(R.drawable.ic_favorite_24px)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imgLike)
        }else{
            photo.like = true
            Glide.with(this@DetailsFavsFragment)
                .load( R.drawable.ic_favorite_border_24px)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imgLike)
        }
    }
}
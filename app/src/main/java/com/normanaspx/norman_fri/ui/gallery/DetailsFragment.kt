package com.normanaspx.norman_fri.ui.gallery

import android.content.Intent
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.normanaspx.norman_fri.R
import com.normanaspx.norman_fri.databinding.FragmentDetailsBinding
import kotlinx.android.synthetic.main.fragment_details.*


/**
Creada por Norman el 3/15/2021
 **/
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args by navArgs<DetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailsBinding.bind(view)

        binding.apply {
            val photo = args.photo

            Glide.with(this@DetailsFragment)
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

            //image
            textViewDesc.text = photo.description
            textViewUsername.apply {
                text = "@${photo.user.username}"
                setTypeface(null, Typeface.BOLD)
            }

            imgLike.setOnClickListener {
                var draw: Int = R.drawable.ic_favorite_border_24px;
                if(photo.like){
                    photo.like = false
                    draw = R.drawable.ic_favorite_24px;
                }else{
                    photo.like = true
                    draw = R.drawable.ic_favorite_border_24px;
                }
                Glide.with(this@DetailsFragment)
                    .load(draw)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imgLike)
            }

            //user
            textViewName.text=photo.user.name
            textViewBio.text=photo.user.bio

        }
    }
}
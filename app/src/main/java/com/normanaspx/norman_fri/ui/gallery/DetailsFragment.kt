package com.normanaspx.norman_fri.ui.gallery

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.normanaspx.norman_fri.R
import com.normanaspx.norman_fri.data.models.PhotoEntity
import com.normanaspx.norman_fri.data.models.PhotoWithDetails
import com.normanaspx.norman_fri.data.models.UrlsEntity
import com.normanaspx.norman_fri.data.models.UserEntity
import com.normanaspx.norman_fri.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream


/**
Creada por Norman el 3/15/2021
 **/
@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args by navArgs<DetailsFragmentArgs>()
    private val userViewModel: GalleryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lateinit var bit: Bitmap
        val binding = FragmentDetailsBinding.bind(view)

        binding.apply {
            val photo = args.photo
            lifecycleScope.launch{
                bit =  getBitmap(photo.urls.regular!!)
                this@DetailsFragment.context?.let { it1 -> saveToInternalStorage(it1, bit, photo.id) }
            }
            Glide.with(this@DetailsFragment)
                .load(photo.urls.regular)
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
                if(photo.like){
                    photo.like = false
                    Glide.with(this@DetailsFragment)
                        .load(R.drawable.ic_favorite_border_24px)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imgLike)
                }else{
                    photo.like = true
                    Glide.with(this@DetailsFragment)
                        .load( R.drawable.ic_favorite_24px)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imgLike)
                }

                if(photo.user.bio.isNullOrEmpty()) photo.user.bio = ""
                val photoDetail: PhotoWithDetails = PhotoWithDetails(
                    PhotoEntity(photo.id, photo.description, photo.likes, photo.like),
                    UrlsEntity(photo.urls.raw, photo.urls.full, photo.urls.regular,photo.id),
                    UserEntity(photo.user.id, photo.user.username, photo.user.name, photo.user.bio, photo.id)
                )




                userViewModel.insert(photoDetail)
            }

            //user
            textViewName.text=photo.user.name
            textViewBio.text=photo.user.bio


        }
    }


    private suspend fun getBitmap(img: String ): Bitmap {
        val loading = this@DetailsFragment.context?.let { ImageLoader(it) }
        val request = this@DetailsFragment.context?.let {
            ImageRequest.Builder(it)
                .data(img)
                .build()
        }

        val result = (request?.let { loading?.execute(it) } as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    private fun saveToInternalStorage(context: Context, bitmapImage: Bitmap, imageFileName: String): String {
        context.openFileOutput(imageFileName, Context.MODE_PRIVATE).use { fos ->
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 25, fos)
        }
        return context.filesDir.absolutePath
    }



}
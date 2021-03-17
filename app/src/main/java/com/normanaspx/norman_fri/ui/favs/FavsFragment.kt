package com.normanaspx.norman_fri.ui.favs

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.normanaspx.norman_fri.R
import com.normanaspx.norman_fri.data.Photo
import com.normanaspx.norman_fri.data.models.PhotoWithDetails
import com.normanaspx.norman_fri.ui.gallery.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.normanaspx.norman_fri.databinding.FragmentFavsBinding
import kotlinx.android.synthetic.main.item_photo.*
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


/**
Creada por Norman el 3/13/2021
 **/

@AndroidEntryPoint
class FavsFragment : Fragment(R.layout.fragment_favs), FavsAdapter.OnItemClickListener {

    private val userViewModel: GalleryViewModel by viewModels()
    private var _binding: FragmentFavsBinding? = null
    private val binding get() = _binding!!
    var ls: ArrayList<PhotoWithDetails> = ArrayList()
    var photos: ArrayList<Photo> = ArrayList()
    var newList: ArrayList<Photo> = ArrayList()

    lateinit var photoadapter: FavsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            _binding = FragmentFavsBinding.bind(view)
            photoadapter = FavsAdapter(this, photos)
            binding.apply {
                recyclerView.setHasFixedSize(false)
                recyclerView.adapter = photoadapter
            }


            if (ls.size == 0) {
                userViewModel.getUser.observe(viewLifecycleOwner, Observer { response ->
                    ls = response as ArrayList<PhotoWithDetails>
                    for (item in ls) {
                        val p: Photo = Photo(
                            item.photo.id,
                            item.photo.description,
                            item.photo.likes,
                            item.photo.like,
                            Photo.Urls(item.urls!!.raw, item.urls.full, item.urls.regular,
                                "", "",item.urls.image),
                            Photo.User(
                                item.user!!.idUser,
                                item.user.name,
                                item.user.username,
                                item.user.bio,
                                "",
                                ""
                            )
                        )
                        photos.add(p)
                    }
                    photoadapter.setUser(photos)
                })
            }
            setHasOptionsMenu(true)
        } catch (e: Exception) {
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu_gallery, menu)

        val searchItem = menu.findItem(R.id.action_search)
        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {

                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    photoadapter.filter.filter(newText)
                    return false

                }
            })
        }
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onItemClick(photo: Photo) {
        val action = FavsFragmentDirections.actionFavsFragmentToDetailsFavsFragment(photo)
        findNavController().navigate(action)
    }

    private suspend fun getBitmap(img: String): Bitmap {
        val loading = this@FavsFragment.context?.let { ImageLoader(it) }
        val request =  this@FavsFragment.context?.let {
            ImageRequest.Builder(it)
                .data(img)
                .build()
        }

        val result = (request?.let { loading?.execute(it) } as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }
}


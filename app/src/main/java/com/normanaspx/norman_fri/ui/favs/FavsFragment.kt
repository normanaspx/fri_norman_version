package com.normanaspx.norman_fri.ui.favs

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.normanaspx.norman_fri.R
import com.normanaspx.norman_fri.data.Photo
import com.normanaspx.norman_fri.data.models.PhotoWithDetails
import com.normanaspx.norman_fri.databinding.FragmentFavsBinding
import com.normanaspx.norman_fri.ui.gallery.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_photo.*
import java.io.File
import java.io.FileInputStream
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
                            Photo.Urls(
                                item.urls!!.raw, item.urls.full, item.urls.regular,
                                "", "",
                                getImageFromInternalStorage(
                                    this@FavsFragment.context,
                                    item.photo.id
                                )
                            ),
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
    private fun getImageFromInternalStorage(context: Context?, imageFileName: String): Bitmap {
        val directory = context?.filesDir
        val file = File(directory, imageFileName)
        return BitmapFactory.decodeStream(FileInputStream(file))
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(photo: Photo) {
        val action = FavsFragmentDirections.actionFavsFragmentToDetailsFavsFragment(photo)
        findNavController().navigate(action)

        this.closefragment()
    }

    private fun closefragment() {

    }

}


package com.example.gallery.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.GridLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gallery.GalleryAdapter

import com.example.gallery.R
import kotlinx.android.synthetic.main.grallery_fragment.*

class GralleryFragment : Fragment() {

    companion object {
        fun newInstance() = GralleryFragment()
    }

    private lateinit var viewModel: GralleryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.grallery_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        val galleryAdapter = GalleryAdapter()
        viewModel = ViewModelProviders.of(
            this,
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(GralleryViewModel::class.java)
        recyclerView.apply {

            adapter = galleryAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)

        }
        viewModel.photoLiveData.observe(this, Observer {

            galleryAdapter.submitList(it)
            sw.isRefreshing = false
        })

        viewModel.photoLiveData.value ?: viewModel.fetchData()

        sw.setOnRefreshListener {
            viewModel.fetchData()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.right_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.swip -> {
                sw.isRefreshing = true
                Handler().postDelayed(Runnable {
                    viewModel.fetchData()


                },1000)


            }


        }
        return super.onOptionsItemSelected(item)


    }
}

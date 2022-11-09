package com.example.pagergallery.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pagergallery.adapter.GalleryAdapter

import com.example.pagergallery.R
import kotlinx.android.synthetic.main.grallery_fragment.*

class GalleryFragment : Fragment() {

    companion object {
        fun newInstance() = GalleryFragment()
    }

    private lateinit var viewModel: GalleryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.grallery_fragment, container, false)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)

        viewModel = ViewModelProviders.of(
            this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(GalleryViewModel::class.java)

        val galleryAdapter = GalleryAdapter(viewModel)


        recyclerView.apply {

            adapter = galleryAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


        }

        viewModel.photoLiveData.observe(viewLifecycleOwner, Observer {

            if (viewModel.needtoScreeoTop) {

                recyclerView.scrollToPosition(0)
                viewModel.needtoScreeoTop = false

            }
            galleryAdapter.submitList(it)
            sw.isRefreshing = false
        })

        viewModel.statueLiveData.observe(viewLifecycleOwner, Observer {
            galleryAdapter.notifyItemChanged(galleryAdapter.itemCount-1)
            galleryAdapter.footerViewStatue=it

            if (it== NET_ERRE){
                sw.isRefreshing = false
            }
        })

        viewModel.photoLiveData.value ?: viewModel.fetchData()

//        viewModel.requs()

        sw.setOnRefreshListener {
            viewModel.fetchData()

        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy < 0) return

                val layoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager
                val intra = IntArray(2)
                layoutManager.findLastCompletelyVisibleItemPositions(intra)
                if (intra[0] == (galleryAdapter.itemCount) - 1) {
                    viewModel.fetchData()

                }
            }


        })
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


                }, 1000)


            }


        }
        return super.onOptionsItemSelected(item)


    }
}

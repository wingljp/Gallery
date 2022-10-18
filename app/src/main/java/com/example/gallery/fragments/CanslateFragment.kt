package com.example.gallery.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.gallery.R

class CanslateFragment : Fragment() {

    companion object {
        fun newInstance() = CanslateFragment()
    }

    private lateinit var viewModel: CanslateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.canslate_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CanslateViewModel::class.java)
        // TODO: Use the ViewModel
    }

}

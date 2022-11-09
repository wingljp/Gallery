package com.example.pagergallery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pagergallery.ViewModel.IconViewModel
import com.example.pagergallery.adapter.IconReAdapter
import kotlinx.android.synthetic.main.activity_icon_list.*
import kotlinx.android.synthetic.main.grallery_fragment.*
import kotlinx.coroutines.flow.collectLatest


class IconListActivity : AppCompatActivity() {
    lateinit var reAdapter: IconReAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_icon_list)
        initRecyclerView();
        initViewModel()
    }

    private fun initRecyclerView() {
        re.apply {

         layoutManager = LinearLayoutManager(this@IconListActivity)

            val decortion =
                DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
            addItemDecoration(decortion)
            reAdapter=IconReAdapter()
            adapter = reAdapter


        }


    }

    private fun initViewModel() {

      val viewmodel=ViewModelProvider(this).get(IconViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            viewmodel.getListData().collectLatest {

                reAdapter.submitData(it)
            }


        }

















    }
}
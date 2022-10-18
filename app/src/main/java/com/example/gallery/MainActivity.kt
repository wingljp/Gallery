package com.example.gallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gallery.fragments.CanslateFragment
import com.example.gallery.fragments.RotateFragment
import com.example.gallery.fragments.ScaleFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewpager2.adapter = object : FragmentStateAdapter(this) {

            override fun getItemCount() = 3

            override fun createFragment(position: Int) =
                when (position) {
                    0 -> ScaleFragment()
                    1 -> RotateFragment()

                    else -> CanslateFragment()

                }

        }
     TabLayoutMediator(tab_layout,viewpager2) { tab, position ->
         when (position) {

             0 -> tab.text = "缩放"
             1 -> tab.text = "旋转"
             else -> tab.text = "移动"
         }


     }.attach()
    }
}

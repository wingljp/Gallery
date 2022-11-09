package com.example.pagergallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        if (savedInstanceState == null) {
//            AcUtil().apply {
//                addFragment(GralleryFragment(), R.id.fragment_container_view);
//
//            }
//        }

//        NavigationUI.setupActionBarWithNavController(this,findNavController(R.id.fragmentContainerView))
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()||findNavController(R.id.fragmentContainerView).navigateUp()
    }

}

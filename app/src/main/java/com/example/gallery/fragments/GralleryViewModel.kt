package com.example.gallery.fragments

import android.app.ActivityOptions
import android.app.Application
import android.os.Build
import android.util.Log
import android.util.Log.DEBUG
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog.DEBUG
import com.android.volley.toolbox.StringRequest
import com.example.gallery.VolleySingleton
import com.example.gallery.modes.PhotoItem
import com.example.gallery.modes.baobei
import com.google.gson.Gson

class GralleryViewModel(application: Application) : AndroidViewModel(application) {
    private val _photoLiveList = MutableLiveData<List<PhotoItem>>()
    val photoLiveData: LiveData<List<PhotoItem>>
        get() = _photoLiveList

    fun fetchData() {
        val stringRequest = StringRequest(

            Request.Method.GET, getUrl(), {

                _photoLiveList.value = Gson().fromJson(it, baobei::class.java).hits.toList()
            }, {

                Log.d("hello", it.toString())
            }


        )
        VolleySingleton.getInstance(getApplication()).requestQueue.add(stringRequest)

    }


    private fun getUrl(): String {

        return "https://pixabay.com/api/?key=30514833-2f7a221fa5c261cf3c03d4b41&q=${keys.random()}&per_page=100"
    }

    private val keys = arrayOf("computer", "cat", "dog", "sheep", "photo", "car")

}

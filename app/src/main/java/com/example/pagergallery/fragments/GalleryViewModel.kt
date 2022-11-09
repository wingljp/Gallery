package com.example.pagergallery.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.example.pagergallery.VolleySingleton
import com.example.pagergallery.modes.PhotoItem
import com.example.pagergallery.modes.PhotoDatas
import com.google.gson.Gson
import kotlin.math.ceil

const val CAN_LOAD = 1
const val IS_DONE = 0
const val NET_ERRE = 2

class GalleryViewModel(application: Application) : AndroidViewModel(application) {
    private val _dataStatusLive = MutableLiveData<Int>()
    val statueLiveData: LiveData<Int>
        get() = _dataStatusLive

    private val _photoLiveList = MutableLiveData<List<PhotoItem>>()
    val photoLiveData: LiveData<List<PhotoItem>>
        get() = _photoLiveList

    private var perPage = 100
    private var cureentPage = 1
    private var totalPage = 1
    private var currentKey = "cat"
    private var isNew = true
    private var isLoading = false
    var needtoScreeoTop = true//回到顶端
    private val keys = arrayOf("computer", "cat", "dog", "sheep", "photo", "car")

    init {
        request()
    }

    private fun request() {
        cureentPage = 1
        totalPage = 1
        currentKey = keys.random()
        isNew = true
        needtoScreeoTop = true
    }

    fun fetchData() {
        if (isLoading) return
        isLoading = true
        if (cureentPage > totalPage) {
            _dataStatusLive.value = IS_DONE
            return
        }

        val stringRequest = StringRequest(

            Request.Method.GET, getUrl(), {

                with(Gson().fromJson(it, PhotoDatas::class.java)) {
                    totalPage = ceil(totalHits.toDouble() / perPage).toInt()

                    if (isNew) {
                        _photoLiveList.value = hits.toList()

                    } else {
                        _photoLiveList.value =
                            arrayListOf(_photoLiveList.value!!, hits.toList()).flatten()

                    }
                    isLoading = false
                    isNew = false
                    _dataStatusLive.value = CAN_LOAD

                }

//
            }, {
                isLoading = false
                isNew = false
                cureentPage++
                _dataStatusLive.value = NET_ERRE

            }


        )
        VolleySingleton.getInstance(getApplication()).requestQueue.add(stringRequest)

    }


    private fun getUrl(): String {

        return "https://pixabay.com/api/?key=30514833-2f7a221fa5c261cf3c03d4b41&q=${keys.random()}" + "&per_page=$perPage&$cureentPage"
    }


}

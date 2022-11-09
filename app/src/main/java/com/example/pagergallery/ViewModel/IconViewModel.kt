package com.example.pagergallery.ViewModel

import DataSource.PagingSourced
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.pagergallery.modes.Resultd

import com.example.pagergallery.network.RetroInstance
import com.example.pagergallery.network.RetrofitApi
import kotlinx.coroutines.flow.Flow


class IconViewModel : ViewModel() {

    var api: RetrofitApi = RetroInstance.getRetrofit().create(RetrofitApi::class.java)

    fun getListData(): Flow<PagingData<Resultd>> {
        return Pager(config = PagingConfig(pageSize = 5, maxSize = 200),
            pagingSourceFactory = { PagingSourced(api) }).flow.cachedIn(viewModelScope)


    }
}
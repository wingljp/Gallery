package com.example.pagergallery.network



import com.example.pagergallery.modes.listDatas
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {

    @GET(value = "character")
    suspend fun getDatas(@Query("page") query: Int): listDatas
}
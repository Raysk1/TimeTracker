package com.raysk.timetracker.data.api


import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient private constructor() {

    private var myApi: APIService

    init {
        val gson = GsonBuilder().setLenient().create()
        val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://192.168.8.61/timetracker/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        myApi = retrofit.create(APIService::class.java)
    }

    fun getApi(): APIService {
        return myApi
    }


    companion object {
        private var instance: RetrofitClient? = null

        @Synchronized
        fun getInstance(): RetrofitClient {
            if (instance == null) {
                instance = RetrofitClient()
            }
            return instance as RetrofitClient
        }


    }

}
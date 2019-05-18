package com.ucsdextandroid1.musicsearch2.data

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Collections.emptyList

/**
 * Created by rjaylward on 2019-05-10
 */
class DataSources private constructor() {

    private val itunesApi: ItunesApi = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://itunes.apple.com/")
            .build()
            .create(ItunesApi::class.java)

    fun search(searchTerm: String, callback: Callback<List<SongItem>>) {
        itunesApi.searchItunes(searchTerm).enqueue(object: retrofit2.Callback<ItunesSearchResults> {
            override fun onResponse(call: Call<ItunesSearchResults>, response: Response<ItunesSearchResults>) {
                callback.onDataFetched(if(response.isSuccessful) response.body()?.songs.orEmpty() else emptyList())
            }

            override fun onFailure(call: Call<ItunesSearchResults>, t: Throwable) {
                callback.onDataFetched(emptyList<SongItem>())
            }
        })
    }

    interface Callback<T> {
        fun onDataFetched(data: T)
    }

    interface ItunesApi {
        @GET("search?media=music&limit=25")
        fun searchItunes(@Query("term") term: String): Call<ItunesSearchResults>
    }

    companion object {

        private var instance: DataSources? = null

        @JvmStatic
        fun getInstance(): DataSources {
            if(instance == null)
                instance = DataSources()

            return instance!!
        }
    }
}

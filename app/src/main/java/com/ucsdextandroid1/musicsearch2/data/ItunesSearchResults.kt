package com.ucsdextandroid1.musicsearch2.data

import com.google.gson.annotations.SerializedName

/**
 * Created by rjaylward on 2019-05-11
 */
class ItunesSearchResults {

    @SerializedName("results")
    var songs: List<ItunesSongItem>? = null

}
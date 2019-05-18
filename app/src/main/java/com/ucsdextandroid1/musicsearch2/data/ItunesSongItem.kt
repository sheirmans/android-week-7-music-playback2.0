package com.ucsdextandroid1.musicsearch2.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by rjaylward on 2019-05-18
 */
class ItunesSongItem(
        @SerializedName("trackId") val _trackId: Long = 0,
        @SerializedName("artistName") val _artistName: String? = null,
        @SerializedName("collectionName") val _albumName: String? = null,
        @SerializedName("trackName") val _trackName: String? = null,
        @SerializedName("previewUrl") val _previewUrl: String? = null,
        @SerializedName("artworkUrl100") val _artworkUrl: String? = null,
        @SerializedName("trackTimeMillis") val _trackTimeMillis: Long = 0
) : Parcelable, SongItem {

    override fun getTrackId(): Long = _trackId

    override fun getTrackName(): String? = _trackName

    override fun getArtistName(): String? = _artistName

    override fun getAlbumName(): String? = _albumName

    override fun getArtworkUrl(): String? = _artworkUrl?.replace("100x100", "500x500")

    override fun getPreviewUrl(): String? = _previewUrl

    override fun getTrackTime(): Long = _trackTimeMillis

    constructor(parcel: Parcel): this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(trackId)
        parcel.writeString(artistName)
        parcel.writeString(_albumName)
        parcel.writeString(trackName)
        parcel.writeString(previewUrl)
        parcel.writeString(artworkUrl)
        parcel.writeLong(trackTime)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR: Parcelable.Creator<ItunesSongItem> {
        override fun createFromParcel(parcel: Parcel): ItunesSongItem {
            return ItunesSongItem(parcel)
        }

        override fun newArray(size: Int): Array<ItunesSongItem?> {
            return arrayOfNulls(size)
        }
    }
}
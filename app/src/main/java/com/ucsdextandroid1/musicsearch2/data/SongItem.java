package com.ucsdextandroid1.musicsearch2.data;

import android.os.Parcelable;
import androidx.annotation.NonNull;

import java.util.Objects;

/**
 * Created by rjaylward on 2019-05-10
 */
public interface SongItem extends Parcelable {

    long getTrackId();

    String getTrackName();

    String getArtistName();

    String getAlbumName();

    String getArtworkUrl();

    String getPreviewUrl();

    long getTrackTime();

    default boolean isSameAs(@NonNull SongItem songItem) {
        return Objects.equals(getTrackId(), songItem.getTrackId())
                && Objects.equals(getTrackName(), songItem.getTrackName())
                && Objects.equals(getArtistName(), songItem.getArtistName())
                && Objects.equals(getAlbumName(), songItem.getAlbumName())
                && Objects.equals(getArtworkUrl(), songItem.getArtworkUrl())
                && Objects.equals(getTrackTime(), songItem.getTrackTime());
    }
}
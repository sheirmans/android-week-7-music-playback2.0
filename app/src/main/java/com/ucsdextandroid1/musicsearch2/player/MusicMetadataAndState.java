package com.ucsdextandroid1.musicsearch2.player;

import androidx.annotation.CheckResult;

import com.ucsdextandroid1.musicsearch2.data.SongItem;

/**
 * Created by rjaylward on 2019-05-17
 */
public class MusicMetadataAndState {

    private SongItem metadata;
    private int state;

    public static MusicMetadataAndState create(SongItem metadata, int state) {
        return new MusicMetadataAndState(metadata, state);
    }

    private MusicMetadataAndState(SongItem metadata, int state) {
        this.metadata = metadata;
        this.state = state;
    }

    public SongItem getMetadata() {
        return metadata;
    }

    public int getState() {
        return state;
    }

    @CheckResult
    public MusicMetadataAndState copyWith(SongItem metadata) {
        return new MusicMetadataAndState(metadata, state);
    }

    @CheckResult
    public MusicMetadataAndState copyWith(int state) {
        return new MusicMetadataAndState(metadata, state);
    }

}
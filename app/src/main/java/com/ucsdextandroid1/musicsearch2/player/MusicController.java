package com.ucsdextandroid1.musicsearch2.player;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.ucsdextandroid1.musicsearch2.data.SongItem;

/**
 * Created by rjaylward on 2019-05-17
 */
public class MusicController {

    private static MusicController instance;

    private MutableLiveData<SongItem> metadataLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> stateLiveData = new MutableLiveData<>();
    private MediatorLiveData<MusicMetadataAndState> metadataAndStateLiveData = new MediatorLiveData<>();

    public static MusicController getInstance() {
        if(instance == null)
            instance = new MusicController();

        return instance;
    }

    private MusicController() {
        stateLiveData.setValue(MusicPlayer.STATE_STOPPED);

        metadataAndStateLiveData.addSource(stateLiveData, new Observer<Integer>() {
            @Override
            public void onChanged(Integer state) {
                MusicMetadataAndState current = metadataAndStateLiveData.getValue();
                MusicMetadataAndState next = current != null ? current.copyWith(state) :
                        MusicMetadataAndState.create(null, state);

                metadataAndStateLiveData.setValue(next);
            }
        });

        metadataAndStateLiveData.addSource(metadataLiveData, new Observer<SongItem>() {
            @Override
            public void onChanged(SongItem metadata) {
                MusicMetadataAndState current = metadataAndStateLiveData.getValue();
                MusicMetadataAndState next = current != null ? current.copyWith(metadata) :
                        MusicMetadataAndState.create(metadata, MusicPlayer.STATE_STOPPED);

                metadataAndStateLiveData.setValue(next);
            }
        });
    }

    public void updateMetadata(SongItem metadata) {
        metadataLiveData.setValue(metadata);
    }

    public void updatePlaybackState(int state) {
        stateLiveData.setValue(state);
    }

    public LiveData<MusicMetadataAndState> getMetadataAndStateLiveData() {
        return metadataAndStateLiveData;
    }

}
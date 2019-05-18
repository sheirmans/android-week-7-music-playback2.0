package com.ucsdextandroid1.musicsearch2.player;

import android.media.AudioManager;
import android.media.MediaPlayer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ucsdextandroid1.musicsearch2.data.SongItem;

import java.io.IOException;

/**
 * Created by rjaylward on 2019-05-17
 */
public class MusicPlayer implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnCompletionListener {

    public static final int STATE_STOPPED = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_PLAYING = 2;
    public static final int STATE_PAUSED = 3;

    private int currentState = STATE_STOPPED;

    private boolean mediaPlayerPrepared = false;
    private boolean playOnPrepared = false;

    @Nullable private MediaPlayer mediaPlayer;
    @Nullable private SongItem currentSongItem;

    @Nullable private OnStateChangedListener stateChangedListener;

    @NonNull
    private MediaPlayer createIfNecessary() {
        if(mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnSeekCompleteListener(this);
        }
        else {
            mediaPlayer.reset();
        }

        playOnPrepared = false;
        mediaPlayerPrepared = false;

        return mediaPlayer;
    }

    public void play(@NonNull SongItem songItem) {
        if(currentSongItem == null || songItem.getTrackId() != currentSongItem.getTrackId()) {
            MediaPlayer mp = createIfNecessary();
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                currentSongItem = songItem;
                mp.setDataSource(songItem.getPreviewUrl());
                mediaPlayerPrepared = false;
                playOnPrepared = true;
                mp.prepareAsync();
                onStateChanged(STATE_LOADING);
            }
            catch(IOException e) {
                e.printStackTrace();
                stop();
            }
        }
        else {
            resume();
        }
    }

    public void resume() {
        if(mediaPlayer != null) {
            if(!mediaPlayer.isPlaying())
                mediaPlayer.start();

            onStateChanged(STATE_PLAYING);
        }
    }

    public void pause() {
        if(mediaPlayer != null) {
            if(mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                playOnPrepared = false;
            }
            onStateChanged(STATE_PAUSED);
        }
    }

    public void stop() {
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;

            currentSongItem = null;
            playOnPrepared = false;
            mediaPlayerPrepared = false;
        }

        onStateChanged(STATE_STOPPED);
    }

    public void seekTo(int position, boolean playOnSeek) {
        if(mediaPlayer != null) {
            if(mediaPlayer.getCurrentPosition() != position) {
                mediaPlayer.seekTo(position);
                playOnPrepared = playOnSeek;
                mediaPlayerPrepared = false;
                onStateChanged(STATE_LOADING);
            }
            else {
                if(playOnSeek)
                    resume();
            }
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayerPrepared = true;
        if(playOnPrepared)
            resume();
        else
            pause();
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        mediaPlayerPrepared = true;
        if(playOnPrepared)
            resume();
        else
            pause();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        seekTo(0, false);
    }

    private int getCurrentPosition() {
        return mediaPlayer != null && mediaPlayerPrepared ? mediaPlayer.getCurrentPosition() : 0;
    }

    @Nullable
    public SongItem getCurrentSongItem() {
        return currentSongItem;
    }

    private void onStateChanged(int newState) {
        if(currentState != newState) {
            currentState = newState;

            if(stateChangedListener != null)
                stateChangedListener.onStateChanged(currentState);
        }
    }

    public void setStateChangedListener(@Nullable OnStateChangedListener stateChangedListener) {
        this.stateChangedListener = stateChangedListener;
    }

    public interface OnStateChangedListener {
        void onStateChanged(int state);
    }

}

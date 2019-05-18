package com.ucsdextandroid1.musicsearch2.player;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;
import com.ucsdextandroid1.musicsearch2.R;
import com.ucsdextandroid1.musicsearch2.data.SongItem;

/**
 * Created by rjaylward on 2019-05-17
 */
public class MusicControlsManager {

    private UIBuilder ui;

    private OnControlClickListener controlsClickListener;

    public static UIBuilder newUIBuilder() {
        return new UIBuilder();
    }

    private MusicControlsManager(UIBuilder UIBuilder) {
        this.ui = UIBuilder;
    }

    public void setControlsClickListener(OnControlClickListener controlsClickListener) {
        this.controlsClickListener = controlsClickListener;
    }

    public void updateViewState(int state) {
        switch(state) {
            case MusicPlayer.STATE_STOPPED:
                if(ui.rootView != null)
                    ui.rootView.setVisibility(View.GONE);
                if(ui.loadingView != null)
                    ui.loadingView.setVisibility(View.GONE);
                if(ui.playPauseView != null) {
                    ui.playPauseView.setImageResource(ui.playResId);
                    ui.playPauseView.setOnClickListener(null);
                }
                break;
            case MusicPlayer.STATE_LOADING:
                if(ui.rootView != null)
                    ui.rootView.setVisibility(View.VISIBLE);
                if(ui.loadingView != null)
                    ui.loadingView.setVisibility(View.VISIBLE);
                if(ui.playPauseView != null) {
                    ui.playPauseView.setImageResource(ui.playResId);
                    ui.playPauseView.setOnClickListener(null);
                }
                break;
            case MusicPlayer.STATE_PAUSED:
                if(ui.rootView != null)
                    ui.rootView.setVisibility(View.VISIBLE);
                if(ui.loadingView != null)
                    ui.loadingView.setVisibility(View.GONE);
                if(ui.playPauseView != null) {
                    ui.playPauseView.setImageResource(ui.playResId);
                    ui.playPauseView.setOnClickListener(v -> {
                        if(controlsClickListener != null)
                            controlsClickListener.onResumeClicked();
                    });
                }
                break;
            case MusicPlayer.STATE_PLAYING:
                if(ui.rootView != null)
                    ui.rootView.setVisibility(View.VISIBLE);
                if(ui.loadingView != null)
                    ui.loadingView.setVisibility(View.GONE);
                if(ui.playPauseView != null) {
                    ui.playPauseView.setImageResource(ui.pauseResId);
                    ui.playPauseView.setOnClickListener(v -> {
                        if(controlsClickListener != null)
                            controlsClickListener.onPauseClicked();
                    });
                }
                break;
        }
    }

    public void updateViewMetadata(@Nullable SongItem metadata) {
        if(metadata != null) {
            if(ui.labelView != null)
                ui.labelView.setText(String.format("%s • %s", metadata.getTrackName(), metadata.getArtistName()));
            if(ui.trackTitleView != null)
                ui.trackTitleView.setText(metadata.getTrackName());
            if(ui.artistNameView != null)
                ui.artistNameView.setText(metadata.getArtistName());
            if(ui.albumTitleView != null)
                ui.albumTitleView.setText(metadata.getAlbumName());

            if(ui.imageView != null) {
                Picasso.get()
                        .load(metadata.getArtworkUrl())
                        .placeholder(R.color.grey_black)
                        .into(ui.imageView);
            }
        }
        else {
            if(ui.labelView != null)
                ui.labelView.setText("");
            if(ui.trackTitleView != null)
                ui.trackTitleView.setText("");
            if(ui.artistNameView != null)
                ui.artistNameView.setText("");
            if(ui.albumTitleView != null)
                ui.albumTitleView.setText("");

            if(ui.imageView != null)
                ui.imageView.setImageResource(R.color.grey_black);

        }
    }

    public static final class UIBuilder {

        private View rootView;
        private ImageView playPauseView;

        private int playResId = R.drawable.ic_play_circle_outline;
        private int pauseResId = R.drawable.ic_pause_circle_outline;

        private TextView labelView;
        private TextView trackTitleView;
        private TextView artistNameView;
        private TextView albumTitleView;

        private View loadingView;
        private ImageView imageView;

        public UIBuilder() {
        }

        public UIBuilder setVisibilityRootView(View rootView) {
            this.rootView = rootView;
            return this;
        }

        public UIBuilder setPlayPauseView(ImageView playPauseView, @DrawableRes int playRes, @DrawableRes int pauseRes) {
            this.playPauseView = playPauseView;
            this.playResId = playRes;
            this.pauseResId = pauseRes;
            return this;
        }

        public UIBuilder setLabelView(TextView labelView) {
            this.labelView = labelView;
            return this;
        }

        public UIBuilder setTrackTitleView(TextView trackTitleView) {
            this.trackTitleView = trackTitleView;
            return this;
        }

        public UIBuilder setArtistNameView(TextView artistNameView) {
            this.artistNameView = artistNameView;
            return this;
        }

        public UIBuilder setAlbumTitleView(TextView albumTitleView) {
            this.albumTitleView = albumTitleView;
            return this;
        }

        public UIBuilder setLoadingView(View loadingView) {
            this.loadingView = loadingView;
            return this;
        }

        public UIBuilder setImageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public MusicControlsManager build() {
            return new MusicControlsManager(this);
        }

    }

    public interface OnControlClickListener {
        void onResumeClicked();
        void onPauseClicked();
    }
}

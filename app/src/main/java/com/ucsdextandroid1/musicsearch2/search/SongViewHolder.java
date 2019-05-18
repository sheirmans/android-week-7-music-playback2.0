package com.ucsdextandroid1.musicsearch2.search;

import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.ucsdextandroid1.musicsearch2.R;
import com.ucsdextandroid1.musicsearch2.data.SongItem;
import com.ucsdextandroid1.musicsearch2.utils.Utils;

/**
 * Created by rjaylward on 2019-05-10
 */
public class SongViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView titleView;
    private TextView subtitleView;

    private SongItem currentSongItem;
    private OnItemClickListener<SongItem> clickListener;

    public static SongViewHolder inflate(@NonNull ViewGroup parent) {
        return new SongViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_song_item, parent, false));
    }

    private SongViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.vsi_image);
        titleView = itemView.findViewById(R.id.vsi_title);
        subtitleView = itemView.findViewById(R.id.vsi_subtitle);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener != null && currentSongItem != null)
                    clickListener.onItemClicked(currentSongItem);
            }
        });
    }

    public void bind(SongItem songItem) {
        this.currentSongItem = songItem;

        Picasso.get().load(songItem.getArtworkUrl())
                .placeholder(new ColorDrawable(Utils.randomColor()))
                .into(imageView);

        titleView.setText(songItem.getTrackName());
        subtitleView.setText(String.format("%s • %s", songItem.getAlbumName(), songItem.getArtistName()));
    }

    public void setClickListener(OnItemClickListener<SongItem> clickListener) {
        this.clickListener = clickListener;
    }

}

package com.ucsdextandroid1.musicsearch2.search;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.ucsdextandroid1.musicsearch2.data.SongItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rjaylward on 2019-05-10
 */
public class SearchAdapter extends RecyclerView.Adapter<SongViewHolder> {

    private List<? extends SongItem> items = new ArrayList<>();

    private OnItemClickListener<SongItem> onItemClickListener;

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SongViewHolder songViewHolder = SongViewHolder.inflate(parent);
        songViewHolder.setClickListener(item -> {
            if(onItemClickListener != null)
                onItemClickListener.onItemClicked(item);
        });

        return songViewHolder;
    }

    public void submitList(@Nullable List<? extends SongItem> list) {
        this.items = list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<SongItem> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private SongItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
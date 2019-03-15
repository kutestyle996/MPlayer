package com.example.mplayer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.mplayer.R;
import com.example.mplayer.data.model.Song;
import java.util.List;

public class SongHomeAdapter extends RecyclerView.Adapter<SongHomeAdapter.SongViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<Song> mSongs;
    private OnItemClickListener mItemClickListener;

    public SongHomeAdapter(Context context, OnItemClickListener itemClickListener) {
        mLayoutInflater = LayoutInflater.from(context);
        mItemClickListener = itemClickListener;
    }

    @Override
    @NonNull
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                             int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_song, parent, false);
        return new SongViewHolder(view, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, final int position) {
        holder.bindData(mSongs.get(position));
    }

    @Override
    public int getItemCount() {
        return mSongs != null ? mSongs.size() : 0;
    }

    public void setData(List<Song> songs) {
        mSongs = songs;
        notifyDataSetChanged();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextName;
        private TextView mTextArtist;
        private OnItemClickListener mItemClickListener;

        private SongViewHolder(View view, OnItemClickListener itemClickListener) {
            super(view);
            mTextName = view.findViewById(R.id.textName);
            mTextArtist = view.findViewById(R.id.textArtist);
            mItemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }

        private void bindData(Song song) {
            if (song == null) return;
            mTextName.setText(song.getName());
            mTextArtist.setText(song.getArtist());
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onClick(getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }
}

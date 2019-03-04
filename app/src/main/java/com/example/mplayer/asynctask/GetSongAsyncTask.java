package com.example.mplayer.asynctask;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import com.example.mplayer.data.model.Song;
import com.example.mplayer.data.source.UserDataSource;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class GetSongAsyncTask extends AsyncTask <Void, Void, List<Song>> {
    private WeakReference<Context> mContext;
    private UserDataSource.DataCallback mDataCallback;

    public GetSongAsyncTask(Context context, UserDataSource.DataCallback dataCallback) {
        mContext = new WeakReference<>(context);
        mDataCallback = dataCallback;
    }

    @Override
    protected List<Song> doInBackground(Void... voids) {
        return getSongs();
    }

    @Override
    protected void onPostExecute(List<Song> songs) {
        if (mDataCallback != null) mDataCallback.onSuccess(songs);
    }

    private List<Song> getSongs() {
        ContentResolver mContentResolver = mContext.get().getContentResolver();
        List<Song> songs = new ArrayList<>();
        Cursor cursor = mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, null);
        cursor.moveToFirst();

        MediaStore.Audio.Media audioMedia = new MediaStore.Audio.Media();
        int indexData = cursor.getColumnIndex(audioMedia.DATA);
        int indexTitle = cursor.getColumnIndex(audioMedia.TITLE);
        int indexArtist = cursor.getColumnIndex(audioMedia.ARTIST);
        while (!cursor.isAfterLast()) {
            String data = cursor.getString(indexData);
            String title = cursor.getString(indexTitle);
            String artist = cursor.getString(indexArtist);
            Uri uri = Uri.parse(data);
            Song song = new Song(title, artist, uri);
            songs.add(song);
            cursor.moveToNext();
        }
        cursor.close();
        return songs;
    }
}

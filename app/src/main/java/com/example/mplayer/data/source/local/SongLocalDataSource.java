package com.example.mplayer.data.source.local;

import android.content.Context;
import com.example.mplayer.data.source.UserDataSource;
import com.example.mplayer.asynctask.GetSongAsyncTask;

public class SongLocalDataSource implements UserDataSource.Local {
    private static SongLocalDataSource sInstance;
    private Context mContext;

    private SongLocalDataSource(Context context) {
        mContext = context;
    }

    public static SongLocalDataSource getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SongLocalDataSource(context);
        }
        return sInstance;
    }

    @Override
    public void getData(UserDataSource.DataCallback dataCallback) {
        new GetSongAsyncTask(mContext, dataCallback).execute();
    }
}

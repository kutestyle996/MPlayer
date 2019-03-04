package com.example.mplayer.data.repository;

import android.content.Context;
import com.example.mplayer.data.source.UserDataSource;
import com.example.mplayer.data.source.local.SongLocalDataSource;

public class SongRepository implements UserDataSource.Local, UserDataSource.Remote {
    private static SongRepository sInstance;
    private UserDataSource.Local mLocal;
    private UserDataSource.Remote mRemote;

    private SongRepository(SongLocalDataSource songLocalDataSource) {
        mLocal = songLocalDataSource;
    }

    public static SongRepository getInstance(SongLocalDataSource songLocalDataSource) {
        if (sInstance == null) {
            sInstance = new SongRepository(songLocalDataSource);
        }
        return sInstance;
    }

    @Override
    public void getData(UserDataSource.DataCallback dataCallback) {
        mLocal.getData(dataCallback);
    }
}

package com.example.mplayer.home;

import android.content.Context;
import com.example.mplayer.adapter.SongHomeAdapter;
import com.example.mplayer.data.repository.SongRepository;
import com.example.mplayer.data.source.UserDataSource;
import java.util.List;

public class HomePresenter implements HomeContract.Presenter {
    private SongRepository mSongRepository;
    private HomeContract.View mView;


    public HomePresenter(SongRepository songRepository, HomeContract.View view) {
        mSongRepository = songRepository;
        mView = view;
    }

    @Override
    public void doGetDataLocal() {
        mSongRepository.getData(new UserDataSource.DataCallback() {
            @Override
            public void onSuccess(List data) {
                mView.displaySong(data);
            }

            @Override
            public void onFail(String message) {

            }
        });
    }
}

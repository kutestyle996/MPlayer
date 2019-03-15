package com.example.mplayer.play;

import com.example.mplayer.data.repository.SongRepository;

public class PlayPresenter implements PlayContract.Presenter {
    private SongRepository mSongRepository;
    private PlayContract.View mView;
    private MusicController mMusicController;

    public PlayPresenter(SongRepository songRepository, PlayContract.View view, MusicController  musicController) {
        mSongRepository = songRepository;
        mView = view;
        mMusicController = musicController;
    }

    @Override
    public void play() {
        if (!mMusicController.isPlaying()) {
            mMusicController.play();
        } else {
            mMusicController.pause();
        }
        mView.updateUi();
    }

    @Override
    public void previous() {
        mMusicController.previous();
        mView.updateUi();
    }

    @Override
    public void next() {
        mMusicController.next();
        mView.updateUi();
    }

    @Override
    public void seek(int progress) {
        mMusicController.seek((progress * mMusicController.getDuration()) / 100);
        mView.updateUi();
    }
}

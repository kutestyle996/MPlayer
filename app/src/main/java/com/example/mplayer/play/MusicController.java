package com.example.mplayer.play;

import android.content.Context;
import android.media.MediaPlayer;
import com.example.mplayer.data.model.Song;
import java.util.ArrayList;
import java.util.List;

public class MusicController implements Imusic, MediaPlayer.OnCompletionListener, MediaPlayer.OnSeekCompleteListener {
    private Context mContext;
    private MediaPlayer mMediaPlayer;
    private List<Song> mSongs = new ArrayList<>();
    private int mCurrentPosition = 0;
    private boolean isPlaying = true;

    public MusicController(Context context) {
        mContext = context;
    }

    @Override
    public void play() {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(getCurrentPosition());
            mMediaPlayer.start();
            mMediaPlayer.setOnCompletionListener(this);
            isPlaying = true;
        }
    }

    @Override
    public void next() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
        if (mCurrentPosition == mSongs.size() - 1) mCurrentPosition = -1;
        mMediaPlayer = MediaPlayer.create(mContext,
                mSongs.get(++mCurrentPosition).getData());
        if (isPlaying) play();
    }

    @Override
    public void previous() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
        if (mCurrentPosition == 0) mCurrentPosition = mSongs.size();
        mMediaPlayer = MediaPlayer.create(mContext,
                mSongs.get(--mCurrentPosition).getData());
        if (isPlaying) play();
    }

    @Override
    public void pause() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
            isPlaying = false;
        }
    }

    @Override
    public void seek(int position) {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(position);
        }
    }

    @Override
    public int getCurrentPosition() {
        return mMediaPlayer != null ? mMediaPlayer.getCurrentPosition() : 0;
    }

    @Override
    public int currentDurationToPercent() {
        return mMediaPlayer.getCurrentPosition()*100/mMediaPlayer.getDuration();
    }

    @Override
    public int getDuration() {
        return mMediaPlayer != null ? mMediaPlayer.getDuration() : 0;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        next();
    }

    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    public void setData(List<Song> songs, int currentSong) {
        mSongs = songs;
        mCurrentPosition = currentSong;
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
        mMediaPlayer = MediaPlayer.create(mContext, mSongs.get(mCurrentPosition).getData());
        play();
    }

    public String getCurrentSongName(){
        return mSongs.get(mCurrentPosition).getName();
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }
}

package com.example.mplayer.play;

public interface Imusic {
    void play();
    void pause();
    void next();
    void previous();
    void seek(int position);
    int getDuration();
    boolean isPlaying();
    int getCurrentPosition();
    int currentDurationToPercent();
}

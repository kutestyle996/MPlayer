package com.example.mplayer.play;

public class PlayContract {
    interface View {
       void updateUi();
    }

    interface Presenter {
        void play();
        void previous();
        void next();
        void seek(int posititon);
    }
}

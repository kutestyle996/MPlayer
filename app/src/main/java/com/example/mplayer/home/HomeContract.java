package com.example.mplayer.home;

import com.example.mplayer.data.model.Song;

import java.util.List;

public class HomeContract {
    interface View {
        void displaySong(List<Song> songs);
    }

    interface Presenter {
        void doGetDataLocal();
    }
}

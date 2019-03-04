package com.example.mplayer.utils;

public interface Constant {

    interface Notification {
        int NOTIFICATION_ID = 1;
        String CHANNEL_ID = "CHANNEL_TEST";
        String CHANNEL_NAME = "CHANNEL_NAME";
        String CHANNEL_DESCRIPTION = "CHANNEL_DESCRIPTION";
    }

    interface Extras {
        String EXTRA_LIST_MUSIC = "com.MPlayer.extras.EXTRA_LIST_MUSIC";
        String EXTRA_CURRENT_POSITION = "com.MPlayer.extras.EXTRA_CURRENT_POSITION";
        String EXTRA_BUTTON_CLICKED = "com.MPlayer.extras.EXTRA_BUTTON_CLICKED";
    }

    interface Action {
        String ACTION_NOTIFICATION_BUTTON_CLICK = "com.Mplayer.action.ACTION_NOTIFICATION_BUTTON_CLICK";
    }
}

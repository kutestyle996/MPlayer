package com.example.mplayer.utils;

public class TimeFormat {
    public static String formatDuration(int duration) {
        String finalTimerString = "";
        String secondsString = "";
        String minutesSting = "";

        // Convert total duration into time
        int hours = (int) (duration / (1000 * 60 * 60));
        int minutes = (int) (duration % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((duration % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            if (hours < 10)
                finalTimerString = "0" + hours + ":";
            else finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }
        if (minutes < 10) {
            minutesSting = "0" + minutes;
        } else {
            minutesSting = "" + minutes;
        }

        finalTimerString = finalTimerString + minutesSting + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    public static int durationToPercent(int currentDuration, int totalDuration) {
        return currentDuration*100/totalDuration;
    }
}

package com.example.mplayer.data.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable{
    private String mName;
    private String mArtist;
    private Uri mData;

    public Song(String name, String artist, Uri data) {
        mName = name;
        mArtist = artist;
        mData = data;
    }

    protected Song(Parcel in) {
        mName = in.readString();
        mArtist = in.readString();
        mData = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public Uri getData() {
        return mData;
    }

    public String getName() {
        return mName;
    }

    public String getArtist() {
        return mArtist;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mArtist);
        dest.writeParcelable(mData, flags);
    }
}

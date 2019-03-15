package com.example.mplayer.data.source;

import android.content.Context;
import java.util.List;

public interface UserDataSource {
    interface Local {
        void getData(DataCallback dataCallback);
    }

    interface Remote {

    }

    interface DataCallback<T> {
        void onSuccess(List<T> data);

        void onFail(String message);
    }
}

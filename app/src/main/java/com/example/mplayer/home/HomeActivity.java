package com.example.mplayer.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.mplayer.R;
import com.example.mplayer.adapter.SongHomeAdapter;
import com.example.mplayer.data.model.Song;
import com.example.mplayer.data.repository.SongRepository;
import com.example.mplayer.data.source.local.SongLocalDataSource;
import java.util.ArrayList;
import java.util.List;
import static com.example.mplayer.play.PlayActivity.getPlayIntent;

public class HomeActivity extends AppCompatActivity implements HomeContract.View, SongHomeAdapter.OnItemClickListener {
    public static final int REQUEST_STORAGE_PERMISSION = 0;
    private RecyclerView mRecyclerSong;
    private SongHomeAdapter mSongHomeAdapter;
    private HomePresenter mHomePresenter;
    private List<Song> mSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mHomePresenter =
                new HomePresenter(SongRepository.getInstance(SongLocalDataSource.getInstance(this)),
                        this);
        initViews();
        requestPermission();
    }

    @Override
    public void displaySong(List<Song> songs) {
        mSongs = new ArrayList<>();
        mSongs = songs;
        mSongHomeAdapter = new SongHomeAdapter(this, this);
        mSongHomeAdapter.setData(mSongs);
        mRecyclerSong.setAdapter(mSongHomeAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STORAGE_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mHomePresenter.doGetDataLocal();
                } else {
                    Toast.makeText(this, R.string.text_permission_denied,
                            Toast.LENGTH_SHORT).show();
                }
                return;

            default:
                break;
        }
    }

    @Override
    public void onClick(int position) {
        startActivity(getPlayIntent(this, mSongs, position));
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_STORAGE_PERMISSION);
    }

    private void initViews() {
        mRecyclerSong = findViewById(R.id.recycler_list_song);
    }
}

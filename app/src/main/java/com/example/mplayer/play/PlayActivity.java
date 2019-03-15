package com.example.mplayer.play;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.mplayer.R;
import com.example.mplayer.data.model.Song;
import com.example.mplayer.data.repository.SongRepository;
import com.example.mplayer.service.MusicService;
import com.example.mplayer.data.source.local.SongLocalDataSource;
import com.example.mplayer.utils.TimeFormat;

import java.util.ArrayList;
import java.util.List;

import static com.example.mplayer.utils.Constant.Action.ACTION_NOTIFICATION_BUTTON_CLICK;
import static com.example.mplayer.utils.Constant.Extras.EXTRA_BUTTON_CLICKED;
import static com.example.mplayer.utils.Constant.Extras.EXTRA_CURRENT_POSITION;
import static com.example.mplayer.utils.Constant.Extras.EXTRA_LIST_MUSIC;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener, PlayContract.View,
        ServiceConnection, SeekBar.OnSeekBarChangeListener {
    public static final int UPDATE_DELAY = 1000;
    private MusicService mMusicService;
    private ImageButton mButtonPrevious;
    private ImageButton mButtonPlay;
    private ImageButton mButtonNext;
    private SeekBar mSeekBar;
    private TextView mTextStartTime;
    private TextView mTextTotalTime;
    private TextView mTextSongName;
    private PlayContract.Presenter mPresenter;

    @Override
    protected void onStart() {
        super.onStart();
        createService();
        registerReceiver(receiver, new IntentFilter(ACTION_NOTIFICATION_BUTTON_CLICK));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_previous:
                mPresenter.previous();
                break;

            case R.id.button_play:
                mPresenter.play();
                mMusicService.showNotification();
                break;

            case R.id.button_next:
                mPresenter.next();
                break;

            default:
                break;
        }
    }

    @Override
    protected void onStop() {
        unbindService(this);
        unregisterReceiver(receiver);
        super.onStop();
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mMusicService = ((MusicService.LocalBinder) service).getService();
        mPresenter = new PlayPresenter(SongRepository.getInstance(SongLocalDataSource.getInstance(this)),
                this, mMusicService.getMusicController());
        mMusicService
                .getMusicController()
                .setData(getIntent().<Song>getParcelableArrayListExtra(EXTRA_LIST_MUSIC),
                        getIntent().getIntExtra(EXTRA_CURRENT_POSITION, 0));
        updateUi();
        mMusicService.showNotification();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    @Override
    public void updateUi() {
        if (mMusicService.getMusicController().isPlaying()) {
            mButtonPlay.setImageResource(R.drawable.icon_pause);
        } else {
            mButtonPlay.setImageResource(R.drawable.icon_play);
        }
        final Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mTextSongName.setText(mMusicService
                        .getMusicController()
                        .getCurrentSongName());
                mTextTotalTime.setText(TimeFormat.formatDuration(mMusicService
                        .getMusicController()
                        .getDuration()));
                mTextStartTime.setText(TimeFormat.formatDuration(mMusicService
                        .getMusicController()
                        .getCurrentPosition()));
                mSeekBar.setProgress(mMusicService
                        .getMusicController()
                        .currentDurationToPercent());
                mHandler.postDelayed(this, UPDATE_DELAY);
            }
        });
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            mPresenter.seek(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public static Intent getPlayIntent(Context context, List<Song> songs, int position) {
        Intent intent = new Intent(context, PlayActivity.class);
        intent.putParcelableArrayListExtra(EXTRA_LIST_MUSIC, (ArrayList<? extends Parcelable>) songs);
        intent.putExtra(EXTRA_CURRENT_POSITION, position);
        return intent;
    }

    public void initViews() {
        mButtonPrevious = findViewById(R.id.button_previous);
        mButtonPlay = findViewById(R.id.button_play);
        mButtonNext = findViewById(R.id.button_next);
        mSeekBar = findViewById(R.id.seekBar_time);
        mTextStartTime = findViewById(R.id.text_StartTime);
        mTextTotalTime = findViewById(R.id.text_totalTime);
        mTextSongName = findViewById(R.id.text_songName);
        mSeekBar.setOnSeekBarChangeListener(this);
        mButtonNext.setOnClickListener(this);
        mButtonPlay.setOnClickListener(this);
        mButtonPrevious.setOnClickListener(this);
    }

    public void createService() {
        Intent musicSerivce = new Intent(this, MusicService.class);
        startService(musicSerivce);
        bindService(musicSerivce, this, Context.BIND_AUTO_CREATE);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int id = intent.getIntExtra(EXTRA_BUTTON_CLICKED, -1);
            switch (id) {
                case R.id.button_play:
                    if (mMusicService.getMusicController().isPlaying()) {
                        mButtonPlay.setImageResource(R.drawable.icon_play);
                    } else {
                        mButtonPlay.setImageResource(R.drawable.icon_pause);
                    }
                    break;
            }
        }
    };
}

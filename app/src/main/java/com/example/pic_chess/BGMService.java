package com.example.pic_chess;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class BGMService extends Service {
    private MediaPlayer bgmMediaPLayer;
    private float volume;
    private int songID;

    public BGMService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        volume = intent.getFloatExtra("VOLUME", 50f);
        songID = intent.getIntExtra("SONG", 0);

        if (bgmMediaPLayer == null) {
            bgmMediaPLayer = MediaPlayer.create(this, songID);
            bgmMediaPLayer.setLooping(true);
            bgmMediaPLayer.setVolume(volume, volume);
        }
        bgmMediaPLayer.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bgmMediaPLayer.stop();
        if (bgmMediaPLayer != null) {
            bgmMediaPLayer.release();
            bgmMediaPLayer = null;
        }
    }
}
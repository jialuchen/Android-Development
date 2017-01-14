package jialu.cmu.edu.leonard.cohen.ui;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import lip.cmu.com.witnessjayz.R;
/**
 * MusicActivity that plays music.
 * <p>
 * author : jialu chen
 * andrew id: jialuc
 */
public class MusicActivity extends Activity {

    private static final int[] ID_START = {R.id.start_btn1, R.id.start_btn2, R.id.start_btn3};
    private static final int[] ID_PAUSE = {R.id.pause_btn1, R.id.pause_btn2, R.id.pause_btn3};
    private static final int[] ID_RESTART = {R.id.restart_btn1, R.id.restart_btn2, R.id.restart_btn3};
    private static final int[] ID_RAW_RESOURCE = {R.raw.music_demo1, R.raw.music_demo2, R.raw.music_demo3};
    private MediaPlayer mediaPlayer;
    private int[] playbackPosition;
    private int currentPlayTrackNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        Button[] startButton = new Button[3];
        Button[] pauseButton = new Button[3];
        Button[] restartButton = new Button[3];
        playbackPosition = new int[3];
        initStart(startButton);
        initPause(pauseButton);
        initRestart(restartButton);
    }

    private void initStart(Button[] startButton) {
        for (int i = 0; i < startButton.length; i++) {
            startButton[i] = (Button) findViewById(ID_START[i]);
            startButton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        playLocalAudioUsingDescriptor(v.getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void initRestart(Button[] restartButton) {
        for (int k = 0; k < restartButton.length; k++) {
            restartButton[k] = (Button) findViewById(ID_RESTART[k]);
            restartButton[k].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    restartAction(v.getId());
                }
            });
        }
    }

    private void initPause(Button[] pauseButton) {
        for (int j = 0; j < pauseButton.length; j++) {
            playbackPosition[j] = 0;
            pauseButton[j] = (Button) findViewById(ID_PAUSE[j]);
            pauseButton[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pauseAction(v.getId());
                }
            });
        }
    }

    public void restartAction(int id) {

        if (mediaPlayer == null) {
            playLocalAudioUsingDescriptor(id);
            return;
        }
        if (mediaPlayer.isPlaying()) {
            Toast.makeText(MusicActivity.this, R.string.pause_needed, Toast.LENGTH_SHORT).show();
            return;
        }

        switch (id) {
            case R.id.restart_btn1:
                restartButton1();
                break;
            case R.id.restart_btn2:
                restartButton2();
                break;
            case R.id.restart_btn3:
                restartButton3();
                break;
            default:
                break;
        }

    }

    private void restartButton3() {
        AssetFileDescriptor fileDesc;
        try {
            Toast.makeText(MusicActivity.this, R.string.album_99, Toast.LENGTH_SHORT).show();
            currentPlayTrackNumber = R.id.start_btn3;
            fileDesc = getResources().openRawResourceFd(ID_RAW_RESOURCE[2]);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(fileDesc.getFileDescriptor(), fileDesc.getStartOffset(), fileDesc.getLength());
            fileDesc.close();
            mediaPlayer.prepare();
            mediaPlayer.seekTo(playbackPosition[2]);
            mediaPlayer.start();
        } catch (Exception e) {
            Log.e(null, "Restart Error");
            e.printStackTrace();
        }
    }

    private void restartButton2() {
        AssetFileDescriptor fileDesc;
        try {
            Toast.makeText(MusicActivity.this, R.string.city_heart, Toast.LENGTH_SHORT).show();
            currentPlayTrackNumber = R.id.start_btn2;
            fileDesc = getResources().openRawResourceFd(ID_RAW_RESOURCE[1]);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(fileDesc.getFileDescriptor(), fileDesc.getStartOffset(), fileDesc.getLength());
            fileDesc.close();
            mediaPlayer.prepare();
            mediaPlayer.seekTo(playbackPosition[1]);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void restartButton1() {
        AssetFileDescriptor fileDesc;
        try {
            Toast.makeText(MusicActivity.this, R.string.hard_knock, Toast.LENGTH_SHORT).show();
            currentPlayTrackNumber = R.id.start_btn1;

            fileDesc = getResources().openRawResourceFd(ID_RAW_RESOURCE[0]);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(fileDesc.getFileDescriptor(), fileDesc.getStartOffset(), fileDesc.getLength());
            fileDesc.close();
            mediaPlayer.prepare();
            mediaPlayer.seekTo(playbackPosition[0]);
            mediaPlayer.start();

        } catch (Exception e) {
            Log.e(null, "Restart Error");
            e.printStackTrace();
        }
    }


    public void pauseAction(int id) {
        if (mediaPlayer == null) {
            Toast.makeText(MusicActivity.this, R.string.a_song_first, Toast.LENGTH_SHORT).show();
            return;
        }

        // save the corresponding play position and pause the media player

        switch (id) {
            case R.id.pause_btn1:
                if (currentPlayTrackNumber == R.id.start_btn1) {
                    playbackPosition[0] = mediaPlayer.getCurrentPosition();
                    mediaPlayer.pause();
                } else {
                    showNotPlayingToast();
                }
                break;
            case R.id.pause_btn2:
                if (currentPlayTrackNumber == R.id.start_btn2) {
                    playbackPosition[1] = mediaPlayer.getCurrentPosition();

                    mediaPlayer.pause();
                } else {
                    showNotPlayingToast();
                }
                break;

            case R.id.pause_btn3:
                if (currentPlayTrackNumber == R.id.start_btn3) {
                    playbackPosition[2] = mediaPlayer.getCurrentPosition();

                    mediaPlayer.pause();
                } else {
                    showNotPlayingToast();
                }
                break;
            default:
                break;
        }
    }

    private void showNotPlayingToast() {
        Toast.makeText(MusicActivity.this, R.string.song_is_not_playing, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void playLocalAudioUsingDescriptor(int id) {
        if (mediaPlayer != null) {
            releaseMediaPlayer();
        }

        AssetFileDescriptor fileDesc;
        switch (id) {
            case R.id.start_btn1:
            case R.id.restart_btn1:
                currentPlayTrackNumber = R.id.start_btn1;
                fileDesc = getResources().openRawResourceFd(ID_RAW_RESOURCE[0]);
                Toast.makeText(MusicActivity.this, R.string.hard_knock, Toast.LENGTH_SHORT).show();
                break;
            case R.id.start_btn2:
            case R.id.restart_btn2:
                currentPlayTrackNumber = R.id.start_btn2;
                fileDesc = getResources().openRawResourceFd(ID_RAW_RESOURCE[1]);
                Toast.makeText(MusicActivity.this, R.string.city_heart, Toast.LENGTH_SHORT).show();
                break;
            case R.id.start_btn3:
            case R.id.restart_btn3:
                currentPlayTrackNumber = R.id.start_btn3;
                fileDesc = getResources().openRawResourceFd(ID_RAW_RESOURCE[2]);
                Toast.makeText(MusicActivity.this, R.string.album_99, Toast.LENGTH_SHORT).show();
                break;
            default:
                fileDesc = null;
        }

        if (fileDesc != null) {
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(fileDesc.getFileDescriptor(), fileDesc.getStartOffset(), fileDesc.getLength());
                fileDesc.close();
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (Exception e) {
                Log.e(null, "Restart Error");
                e.printStackTrace();
            }
        }
    }


}


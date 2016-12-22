package com.deitel.funnymusic;

/**
 * Created by NgocLoi on 12/6/2016.
 */
import android.app.SearchManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlaySongActivity extends AppCompatActivity
        implements MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener {

    private SpeechRecognizer speechRecognizer;
    private SeekBar seekBar;
    private TextView tvCurrentTime, tvTotalTime, tvSongTitle, tvArtist;
    private ImageView ivCircle;
    private ImageButton btnPlay, btnNext, btnPrevious, btnRepeat, btnShuffle, btnListSongs, btnSpeak;

    private Animation rotateAnimation;

    private int songPosition;
    private ArrayList<Song> songsList;

    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_song_layout);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        tvCurrentTime = (TextView) findViewById(R.id.tvCurrentTime);
        tvTotalTime = (TextView) findViewById(R.id.tvTotalTime);
        tvSongTitle = (TextView) findViewById(R.id.tvSongTitle);
        tvArtist = (TextView) findViewById(R.id.tvArtist);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnPrevious = (ImageButton) findViewById(R.id.btnPrevious);
        btnRepeat = (ImageButton) findViewById(R.id.btnRepeat);
        btnShuffle = (ImageButton) findViewById(R.id.btnShuffle);
        btnListSongs = (ImageButton) findViewById(R.id.btnListSongs);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        ivCircle = (ImageView) findViewById(R.id.ivCircle);

        handler = new Handler();
        songsList = Util.LIST_SONGS_PLAY;

        seekBar.setOnSeekBarChangeListener(this);
        Util.mediaPlayer.setOnCompletionListener(this);

        songPosition = getIntent().getIntExtra(Util.KEY_SONG_POSITION, -1);
        playSong(songPosition);

        rotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);

        setDefaultInfo();
        checkVoiceRecognition();
    }

    public void checkVoiceRecognition() {
        Log.v("", "checkVoiceRecognition checkVoiceRecognition");
        // Kiểm tra thiết bị có cho phép nhận dạnh giọng nói
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0) {
            btnSpeak.setEnabled(false);
            Toast.makeText(this, "Voice recognizer not present", Toast.LENGTH_SHORT).show();
        }
    }

    public void playAction(View v) {
        ivCircle.startAnimation(rotateAnimation);
        if (Util.mediaPlayer.isPlaying()) {
            btnPlay.setBackgroundResource(R.drawable.btn_play_xml);
            Util.mediaPlayer.pause();

        } else {
            btnPlay.setBackgroundResource(R.drawable.btn_pause_xml);
            Util.mediaPlayer.start();
        }
    }

    public void previousAction(View v) {
        ivCircle.startAnimation(rotateAnimation);
        if (Util.IS_SHUFFLE == 1) {
            // shuffle on, random bài songPosition
            Random rand = new Random();
            songPosition = rand.nextInt((songsList.size() - 1) - 0 + 1) + 0;
            playSong(songPosition);
        } else {
            if (songPosition > 0) {
                playSong(songPosition - 1);
                songPosition = songPosition - 1;
            } else {
                playSong(songsList.size() - 1);
                songPosition = songsList.size() - 1;
            }
        }
    }

    public void nextAction(View v) {
        ivCircle.startAnimation(rotateAnimation);
        if (Util.IS_SHUFFLE == 1) {
            // shuffle on, random bài songPosition
            Random rand = new Random();
            songPosition = rand.nextInt((songsList.size() - 1) - 0 + 1) + 0;
            playSong(songPosition);
        } else {
            if (songPosition < songsList.size() - 1) {
                playSong(songPosition + 1);
                songPosition = songPosition + 1;
            } else {
                playSong(0);
                songPosition = 0;
            }
        }
    }

    public void repeatAction(View v) {
        if (Util.IS_REPEAT == 0) {
            Util.IS_REPEAT = 1;
            btnRepeat.setBackgroundResource(R.drawable.btn_repeat_all);
        } else if (Util.IS_REPEAT == 1) {
            Util.IS_REPEAT = 2;
            Util.IS_SHUFFLE = 0;
            btnRepeat.setBackgroundResource(R.drawable.btn_repeat_one);
            btnShuffle.setBackgroundResource(R.drawable.btn_shuffle);
        } else {
            Util.IS_REPEAT = 0;
            btnRepeat.setBackgroundResource(R.drawable.btn_repeat);
        }
    }

    public void shuffleAction(View v) {
        if (Util.IS_SHUFFLE == 0 && Util.IS_REPEAT != 2) {
            Util.IS_SHUFFLE = 1;
            btnShuffle.setBackgroundResource(R.drawable.btn_shuffle_on);
        } else if (Util.IS_SHUFFLE == 0 && Util.IS_REPEAT == 2) {
            Util.IS_SHUFFLE = 1;
            Util.IS_REPEAT = 0;
            btnShuffle.setBackgroundResource(R.drawable.btn_shuffle_on);
            btnRepeat.setBackgroundResource(R.drawable.btn_repeat);
        } else {
            Util.IS_SHUFFLE = 0;
            btnShuffle.setBackgroundResource(R.drawable.btn_shuffle);
        }
    }

    public void playListAction(View v) {
        Intent i = new Intent(getApplicationContext(), PlayListActivity.class);
        startActivityForResult(i, 100);
    }

    public void speakAction(View v) {
        Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        //Xác nhận ứng dụng muốn gửi yêu cầu
        speechIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());
        //Giá trị nhỏ nhất của thời gian nghe
        speechIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 1000);
        //Xác định bạn muốn bao nhiêu kết quả gần đúng được trả về
        speechIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        //Gửi yêu cầu đi
        startActivityForResult(speechIntent, Util.VOICE_RECOGNITION_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Util.VOICE_RECOGNITION_REQUEST_CODE)
            //Trường hợp có giá trị trả về
            if (resultCode == RESULT_OK) {
                ArrayList<String> textMatchList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                if (!textMatchList.isEmpty()) {
                    //Nếu trong list Match có từ khoá seach thì sẽ tiến hành search trên mạng
                    if (textMatchList.get(0).contains("search")) {
                        String searchQuery = textMatchList.get(0).replace("search", " ");
                        Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
                        search.putExtra(SearchManager.QUERY, searchQuery);
                        startActivity(search);
                    } else {
                        String str = textMatchList.get(0);
                        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
                        if (str.equalsIgnoreCase(Util.KEY_PLAY)) {
                            if (!Util.mediaPlayer.isPlaying()) {
                                btnPlay.setBackgroundResource(R.drawable.btn_pause_xml);
                                Util.mediaPlayer.start();
                            }
                        } else if (str.equalsIgnoreCase(Util.KEY_PAUSE)) {
                            if (Util.mediaPlayer.isPlaying()) {
                                btnPlay.setBackgroundResource(R.drawable.btn_play_xml);
                                Util.mediaPlayer.pause();
                            }
                        }
                        else if(str.equalsIgnoreCase(Util.KEY_NEXT))
                            nextAction(new View(this));
                        else if(str.equalsIgnoreCase(Util.KEY_PREVIOUS))
                            previousAction(new View(this));
                        else if(str.equalsIgnoreCase(Util.KEY_PLAYLIST))
                            playListAction(new View(this));
                        else if(str.equalsIgnoreCase(Util.KEY_REPEAT))
                            repeatAction(new View(this));
                        else if(str.equalsIgnoreCase(Util.KEY_SHUFFLE))
                            shuffleAction(new View(this));
                        else {
                            Util.LIST_SONGS_SEARCH.clear();
                            String strRemoveAccent = Util.removeAccent(str);
                            for (Song song : Util.getListSongs(this)) {
                                if (song.getTitle().toLowerCase().contains(str.toLowerCase())
                                        || song.getTitle().toLowerCase().contains(strRemoveAccent.toLowerCase())
                                        || song.getAlbum().toLowerCase().contains(str.toLowerCase())
                                        || song.getAlbum().toLowerCase().contains(strRemoveAccent.toLowerCase())
                                        || song.getArtist().toLowerCase().contains(str.toLowerCase())
                                        || song.getArtist().toLowerCase().contains(strRemoveAccent.toLowerCase())) {
                                    Util.LIST_SONGS_SEARCH.add(song);
                                }
                            }
                            if (!Util.LIST_SONGS_SEARCH.isEmpty()) {
                                Util.VOICE_RECOGNIZE = str;
                                Intent intentViewSearch = new Intent(getApplicationContext(), ListSongsSearchActivity.class);
                                startActivityForResult(intentViewSearch, Util.OPEN_ACTIVITY_LIST_SONGS_SEARCH);
                            }
                        }

                    }
                }
                //Các trường hợp lỗi
            } else if (resultCode == RecognizerIntent.RESULT_AUDIO_ERROR) {
                showToastMessage("Audio Error");
            } else if (resultCode == RecognizerIntent.RESULT_CLIENT_ERROR) {
                showToastMessage("Client Error");
            } else if (resultCode == RecognizerIntent.RESULT_NETWORK_ERROR) {
                showToastMessage("Network Error");
            } else if (resultCode == RecognizerIntent.RESULT_NO_MATCH) {
                showToastMessage("No Match");
            } else if (resultCode == RecognizerIntent.RESULT_SERVER_ERROR) {
                showToastMessage("Server Error");
            }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void setDefaultInfo() {
        String songTitle = songsList.get(songPosition).getTitle();
        String songArtist = songsList.get(songPosition).getArtist();

        if (Util.IS_REPEAT == 1) {
            btnRepeat.setBackgroundResource(R.drawable.btn_repeat_all);
        } else if (Util.IS_REPEAT == 2) {
            btnRepeat.setBackgroundResource(R.drawable.btn_repeat_one);
        } else {
            btnRepeat.setBackgroundResource(R.drawable.btn_repeat);
        }

        if (Util.IS_SHUFFLE == 1) {
            btnShuffle.setBackgroundResource(R.drawable.btn_shuffle_on);
        } else {
            btnShuffle.setBackgroundResource(R.drawable.btn_shuffle);
        }
        tvSongTitle.setText(songTitle);
        tvCurrentTime.setText(Util.getTextFormat(0));
        tvTotalTime.setText(Util.getTextFormat(songsList.get(songPosition).getDuration()));
        tvArtist.setText(songArtist);
        seekBar.setMax(Util.mediaPlayer.getDuration());
        seekBar.setProgress(0);
    }

    private Runnable updateSongTime = new Runnable() {
        @Override
        public void run() {
            if (Util.mediaPlayer != null) {
                setDefaultInfo();
                long currentTime = Util.mediaPlayer.getCurrentPosition();
                tvCurrentTime.setText(Util.getTextFormat((int) currentTime));
                seekBar.setMax(Util.mediaPlayer.getDuration());
                seekBar.setProgress((int) currentTime);
                handler.postDelayed(this, Util.DELAY_MILLISECONDS);
            }
        }
    };

    public void playSong(int position) {
        Util.SONG_POSITION = position;
        try {
            Util.mediaPlayer.reset();
            Util.mediaPlayer.setDataSource(songsList.get(position).getPath());
            Util.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            Util.mediaPlayer.prepare();
            Util.mediaPlayer.start();
            btnPlay.setBackgroundResource(R.drawable.btn_pause_xml);
            updateProgressBar();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateProgressBar() {
        handler.postDelayed(updateSongTime, Util.DELAY_MILLISECONDS);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        // Kiểm tra repeat on hay off
        if (Util.IS_REPEAT == 2) {
            playSong(songPosition);
        } else if (Util.IS_SHUFFLE == 1) {
            // shuffle on, random bài songPosition
            Random rand = new Random();
            songPosition = rand.nextInt((songsList.size() - 1) - 0 + 1) + 0;
            playSong(songPosition);
        } else {
            // Repeat và shuffle off, chơi bài tiếp theo
            if (songPosition < (songsList.size() - 1)) {
                playSong(songPosition + 1);
                songPosition = songPosition + 1;
            } else if (Util.IS_REPEAT == 1) {
                //cuối list, chơi bài đầu tiên
                playSong(0);
                songPosition = 0;
            } else {
                Util.mediaPlayer.stop();
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        handler.removeCallbacks(updateSongTime);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        handler.removeCallbacks(updateSongTime);
        Util.mediaPlayer.seekTo(seekBar.getProgress());
        updateProgressBar();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}

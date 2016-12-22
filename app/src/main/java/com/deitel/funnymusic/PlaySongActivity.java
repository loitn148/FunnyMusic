package com.deitel.funnymusic;

/**
 * Created by NgocLoi on 12/2/2016.
 */

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.R.attr.data;
import static android.R.attr.mediaRouteButtonStyle;

public class PlaySongActivity extends AppCompatActivity
        implements MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener{

    private SeekBar seekBar;
    private TextView tvCurrentTime, tvTotalTime, tvSongTitle, tvArtist;
    private ImageButton btnPlay, btnNext, btnPrevious, btnRepeat, btnShuffle, btnListSongs, btnSpeak;

    private boolean isRepeat, isShuffle;
    private int songPosition;
    private int songDuration;
    private ArrayList<Song> songsList;

    private MediaPlayer mediaPlayer;
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

        mediaPlayer = new MediaPlayer();
        handler = new Handler();
        songsList = Util.getListSongs(this);
        isRepeat = false;
        isShuffle = false;

        seekBar.setOnSeekBarChangeListener(this);
        mediaPlayer.setOnCompletionListener(this);
        checkVoiceRecognition();

        songPosition = getIntent().getIntExtra(Util.KEY_SONG_POSITION, 0);
        setDefaultInfo();
        playSong(songPosition);
    }

    public void checkVoiceRecognition() {
        Log.v("", "checkVoiceRecognition checkVoiceRecognition");
        // Kiem tra thiet bi cho phep nhan dang giong noi hay ko
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0) {
            btnSpeak.setEnabled(false);
            Toast.makeText(this, "Voice recognizer not present", Toast.LENGTH_SHORT).show();
        }
    }

    public void playAction(View v) {
        if (mediaPlayer.isPlaying()) {
            btnPlay.setBackgroundResource(R.drawable.btn_play_xml);
            mediaPlayer.pause();
        } else {
            btnPlay.setBackgroundResource(R.drawable.btn_pause_xml);
            mediaPlayer.start();
        }

    }

    public void previousAction(View v) {
        if(songPosition > 0){
            playSong(songPosition - 1);
            songPosition = songPosition - 1;
        }
        else {
            playSong(songsList.size() - 1);
            songPosition = songsList.size() - 1;
        }
    }

    public void nextAction(View v) {
        if(songPosition < songsList.size() - 1){
            playSong(songPosition + 1);
            songPosition = songPosition + 1;
        }
        else {
            playSong(0);
            songPosition = 0;
        }
    }

    public void forwardAction(View v){

        int currentPosition = mediaPlayer.getCurrentPosition();

        if (currentPosition + Util.FORWARD_TIME <= mediaPlayer.getDuration()) {

            mediaPlayer.seekTo(currentPosition + Util.FORWARD_TIME);
        } else {

            mediaPlayer.seekTo(mediaPlayer.getDuration());
        }
    }

    public void backwardAction(View v){
        int currentPosition2 = mediaPlayer.getCurrentPosition();
        // check if seekBackward time is greater than 0 sec
        if (currentPosition2 - Util.BACKWARD_TIME >= 0) {
            // forward song
            mediaPlayer.seekTo(currentPosition2 - Util.BACKWARD_TIME);
        } else {
            // backward to starting position
            mediaPlayer.seekTo(0);
        }
    }

    public void speakAction(View v){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        // xac nhan ung dung muon gui yeu cau
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());

        // goi y nhung dieu nguoi dung muon noi
        //intent.putExtra(RecognizerIntent.EXTRA_PROMPT, metTextHint.getText().toString());

        // goi y nhan dang nhung gi nguoi dung se noi
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);

        /* / Kiem tra item muon hien thi da chon tron spinner
        /if (msTextMatches.getSelectedItemPosition() == AdapterView.INVALID_POSITION) {
            Toast.makeText(this, "Please select No. of Matches from spinner", Toast.LENGTH_SHORT).show();
            return;
        }*/

        //int noOfMatches = Integer.parseInt(msTextMatches.getSelectedItem().toString());

        // Xac dinh ban muon bao nhieu ket qua gan dung duoc tra ve
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);

        // Gui yeu cau di
        startActivityForResult(intent, Util.VOICE_RECOGNITION_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data){
        if (requestCode == Util.VOICE_RECOGNITION_REQUEST_CODE)
            // Truong hop co gia tri tra ve
            if(resultCode == RESULT_OK) {
                ArrayList<String> textMatchList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                if (!textMatchList.isEmpty()) {
                    // kiem tra neu co chua tu khoa 'search' thi se bat dau tim kiem tren web
                    if (textMatchList.get(0).contains("search")) {
                        String searchQuery = textMatchList.get(0).replace("search", " ");
                        Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
                        search.putExtra(SearchManager.QUERY, searchQuery);
                        startActivity(search);
                    } else {
                        String pause = "pause";
                        String play = "play";
                        String next = "next";
                        String previous = "previous";
                        // Hien thi ket qua
                        //mlvTextMatches.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, textMatchList));
                        for(String str : textMatchList){

                            if(str.toString().equals(next)){
                                if(songPosition < songsList.size() - 1){
                                    playSong(songPosition + 1);
                                    songPosition = songPosition + 1;
                                }
                                else {
                                    playSong(0);
                                    songPosition = 0;
                                }
                            }
                        }
                    }
                }
                // Cac truong hop loi
            } else if (resultCode == RecognizerIntent.RESULT_AUDIO_ERROR){
                showToastMessage("Audio Error");
            } else if (resultCode == RecognizerIntent.RESULT_CLIENT_ERROR){
                showToastMessage("Client Error");
            } else if (resultCode == RecognizerIntent.RESULT_NETWORK_ERROR){
                showToastMessage("Network Error");
            } else if (resultCode == RecognizerIntent.RESULT_NO_MATCH){
                showToastMessage("No Match");
            } else if (resultCode == RecognizerIntent.RESULT_SERVER_ERROR){
                showToastMessage("Server Error");
            }
        super.onActivityResult(requestCode, resultCode, data);
    }

    void showToastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    private void setDefaultInfo() {
        String songTitle = songsList.get(songPosition).getTitle();
        String songArtist = songsList.get(songPosition).getArtist();
        tvSongTitle.setText(songTitle);
        tvCurrentTime.setText(Util.getTextFormat(0));
        tvTotalTime.setText(Util.getTextFormat(songsList.get(songPosition).getDuration()));
        tvArtist.setText(songArtist);
        seekBar.setMax(songDuration);
        seekBar.setProgress(0);
    }

    private Runnable updateSongTime = new Runnable() {
        @Override
        public void run() {
            if(mediaPlayer != null) {
                setDefaultInfo();
                long currentTime = mediaPlayer.getCurrentPosition();
                tvCurrentTime.setText(Util.getTextFormat((int) currentTime));
                seekBar.setMax(mediaPlayer.getDuration());
                seekBar.setProgress((int) currentTime);
                handler.postDelayed(this, Util.DELAY_MILLISECONDS);
            }
        }
    };

    public void playSong(int position){
        try{
            mediaPlayer.reset();
            mediaPlayer.setDataSource(songsList.get(songPosition).getPath());
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.start();
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

    public void updateProgressBar(){
        handler.postDelayed(updateSongTime, Util.DELAY_MILLISECONDS);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        // Kiểm tra repeat on hay off
        if(isRepeat){
            playSong(songPosition);
        } else if(isShuffle){
            // shuffle on, random bài songPosition
            Random rand = new Random();
            songPosition = rand.nextInt((songsList.size() - 1) - 0 + 1) + 0;
            playSong(songPosition);
        } else{
            // Repeat và shuffle off, chơi bài tiếp theo
            if(songPosition < (songsList.size() - 1)){
                playSong(songPosition + 1);
                songPosition = songPosition + 1;
            }else{
                //cuối list, chơi bài đầu tiên
                playSong(0);
                songPosition = 0;
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
        mediaPlayer.seekTo(seekBar.getProgress());
        updateProgressBar();
    }
}
package com.deitel.funnymusic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.R.attr.delay;

/**
 * Created by NgocLoi on 12/13/2016.
 */

public class LaunchAppActivity extends Activity {

    Animation translate, fade_in;
    ImageView ivLaunchApp;
    TextView tvLaunchApp;
    Handler handler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_app);

        ivLaunchApp = (ImageView) findViewById(R.id.ivLaunchApp);
        tvLaunchApp = (TextView) findViewById(R.id.tvLaunchApp);

        translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        fade_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        ivLaunchApp.startAnimation(translate);
        tvLaunchApp.startAnimation(fade_in);

        handler = new Handler();

        Util.LIST_SONGS = Util.getListSongs(this);
        Util.LIST_ALBUMS = Util.getListAlbums(this);
        handler.postDelayed(delay, 2500);

    }

    private Runnable delay = new Runnable() {
        @Override
        public void run() {
            Intent startIntent = new Intent(getApplicationContext(), StartActivity.class);
            startActivity(startIntent);

        }
    };
}

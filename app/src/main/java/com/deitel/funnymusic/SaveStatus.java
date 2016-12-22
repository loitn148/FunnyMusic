package com.deitel.funnymusic;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by NgocLoi on 12/10/2016.
 */

public class SaveStatus {
    public static void savingStatus(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Status App", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("isRepeat", Util.IS_REPEAT);
        editor.putInt("isShuffle", Util.IS_SHUFFLE);
    }

    public static void loadStatusApp(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Status App", MODE_PRIVATE);
        if(sharedPreferences != null){
            Util.IS_REPEAT = sharedPreferences.getInt("isRepeat", 0);
            Util.IS_SHUFFLE = sharedPreferences.getInt("isShuffle", 0);
        }else{
            Util.IS_REPEAT = 0;
            Util.IS_SHUFFLE = 0;
        }
    }

}

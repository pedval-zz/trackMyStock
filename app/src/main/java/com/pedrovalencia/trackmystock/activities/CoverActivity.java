package com.pedrovalencia.trackmystock.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.pedrovalencia.trackmystock.R;
import com.pedrovalencia.trackmystock.util.SystemUiHider;

import java.io.IOException;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class CoverActivity extends Activity {

    public static final int DELAY = 3000;
    private static final String LOG_TAG = CoverActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Block that hides the status bar
        if(Build.VERSION.SDK_INT < 16) {
            //For version lower than Jellybean
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            //For version greater or equal than Jellybean
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            // We should hide the action bar if the status bar is hiden as well
            ActionBar actionBar = getActionBar();
            actionBar.hide();
        }
        setContentView(R.layout.activity_cover);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException ex) {
            Log.e(LOG_TAG, "Error while waiting for the delay "+DELAY +" milliseconds", ex);
        } finally{
            //Start new activity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            //Destroy the current activity. We don't want it in the stack
            //this.finish();
        }
    }
}

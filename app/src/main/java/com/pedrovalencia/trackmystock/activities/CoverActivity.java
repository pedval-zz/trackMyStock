package com.pedrovalencia.trackmystock.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import com.pedrovalencia.trackmystock.R;


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

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent(CoverActivity.this, EmptyListActivity.class);
                startActivity(intent);
                //Destroy the current activity. We don't want it in the stack*/
                CoverActivity.this.finish();
            }
        }, DELAY);
    }
}

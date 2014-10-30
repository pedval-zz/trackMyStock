package com.pedrovalencia.trackmystock.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;

import com.pedrovalencia.trackmystock.R;
import com.pedrovalencia.trackmystock.data.CompanyContract;


public class CoverActivity extends Activity {

    //Milliseconds between CoverActiviy and next Activity
    public static final int DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                Cursor cursor = getContentResolver().query(CompanyContract.CompanyEntry.CONTENT_URI,null,null,null,null);
                Intent intent;
                if(cursor.getCount() > 0) {
                    intent = new Intent(CoverActivity.this, CompanyListActivity.class);
                } else {
                    intent = new Intent(CoverActivity.this, EmptyListActivity.class);
                }
                startActivity(intent);
                //Destroy the current activity. We don't want it in the stack*/
                CoverActivity.this.finish();
            }
        }, DELAY);
    }
}

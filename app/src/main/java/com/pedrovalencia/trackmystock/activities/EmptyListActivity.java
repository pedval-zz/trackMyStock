package com.pedrovalencia.trackmystock.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pedrovalencia.trackmystock.R;

public class EmptyListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_list);
    }


    /**
     * Method that redirects to AddCompanyActivity
     */
    public void goToAddCompanyActivity(View view) {
        //Create new intent and we reuse if there is an instance already created
        Intent intent = new Intent(this, AddCompanyActivity.class);
        startActivity(intent);
    }
}

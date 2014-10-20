package com.pedrovalencia.trackmystock.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.pedrovalencia.trackmystock.R;

public class AddCompanyActivity extends ActionBarActivity {

    private ArrayAdapter<String> mCompanyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);

        //TODO this list must be dynamic
        String[] companyList = new String[] { "Google Inc.", "Yahoo Inc.", "Microsoft", "Nextub",
                "TrackMyStock"};

        //Initialize adapter
        //TODO. The adapter must inject the companyList dynamically
        mCompanyAdapter = new ArrayAdapter<String>(this, R.layout.simple_company_item, companyList);

        //Link adapter to listView
        ListView listView = (ListView)findViewById(R.id.add_company_list_view);
        listView.setAdapter(mCompanyAdapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_company, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

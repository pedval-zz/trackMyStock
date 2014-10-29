package com.pedrovalencia.trackmystock.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.pedrovalencia.trackmystock.R;
import com.pedrovalencia.trackmystock.adapters.CompanySearchAdapter;
import com.pedrovalencia.trackmystock.data.CompanyContract;
import com.pedrovalencia.trackmystock.domain.CompanyDetail;
import com.pedrovalencia.trackmystock.domain.CompanySignature;
import com.pedrovalencia.trackmystock.util.CompanySearchUtil;

public class AddCompanyActivity extends ActionBarActivity {

    private CompanySignature mCompanySignature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);

        //Initialize adapter and link adapter to listView
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.add_company_list_view);
        autoCompleteTextView.setAdapter(new CompanySearchAdapter(this, R.layout.simple_company_item));

        //Listener to enable the button when customer selects one company
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                findViewById(R.id.add_company_button).setEnabled(true);
            }
        });

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
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Handles the Accept button to go to CompanyListActivity
     * @param view
     */
    public void goToCompanyListActivity(View view) {
        //1.- Get all data from view
        AutoCompleteTextView textView = (AutoCompleteTextView)findViewById(R.id.add_company_list_view);
        CompanySignature companySignature = ((CompanySignature)textView.getAdapter().getItem(textView.getListSelection()));

        //2.- Bring info for this company
        CompanyDetail companyDetail = CompanySearchUtil.getDetail(companySignature.getSymbol());

        //3.- Store that company info
        ContentValues values = new ContentValues();
        values.put(CompanyContract.CompanyEntry.COLUMN_SYMBOL, companyDetail.getCompanySignature().getSymbol());
        values.put(CompanyContract.CompanyEntry.COLUMN_NAME, companyDetail.getCompanySignature().getName());
        values.put(CompanyContract.CompanyEntry.COLUMN_LAST_UPDATE, companyDetail.getDate());
        values.put(CompanyContract.CompanyEntry.COLUMN_PRICE, companyDetail.getPrice());
        values.put(CompanyContract.CompanyEntry.COLUMN_HIGH, companyDetail.getHigh());
        values.put(CompanyContract.CompanyEntry.COLUMN_LOW, companyDetail.getLow());
        values.put(CompanyContract.CompanyEntry.COLUMN_CHANGE, companyDetail.getChange());
        getContentResolver().insert(CompanyContract.CompanyEntry.CONTENT_URI, values);

        //4.- Go to CompanyListActivity
        Intent intent = new Intent(this, CompanyListActivity.class);
        startActivity(intent);
    }
}

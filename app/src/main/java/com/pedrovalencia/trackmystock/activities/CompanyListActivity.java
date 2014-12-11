package com.pedrovalencia.trackmystock.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.pedrovalencia.trackmystock.R;
import com.pedrovalencia.trackmystock.data.CompanyContract;
import com.pedrovalencia.trackmystock.fragments.CompanyListFragment;
import com.pedrovalencia.trackmystock.fragments.DetailFragment;
import com.pedrovalencia.trackmystock.fragments.HistoricFragment;
import com.pedrovalencia.trackmystock.sync.TrackMyStockSyncAdapter;


public class CompanyListActivity extends ActionBarActivity implements CompanyListFragment.Callback{

    private boolean mTwoPane;
    private String mSymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_company_list);

        if(findViewById(R.id.detail_id) != null) {
            //Tablet version
            mTwoPane = true;

            mSymbol = getIntent().getStringExtra(DetailActivity.SYMBOL);

            //Check if the we have already a company saved
            if(mSymbol == null || mSymbol.isEmpty()) {
                Cursor cursor = getContentResolver().query(
                        CompanyContract.CompanyEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        null);

                if(cursor.moveToFirst()) {
                    mSymbol = cursor.getString(cursor.getColumnIndex(CompanyContract.CompanyEntry.COLUMN_SYMBOL));
                }
                cursor.close();
            }
            Bundle args = new Bundle();
            args.putString(DetailActivity.SYMBOL, mSymbol);

            DetailFragment detailFragment = new DetailFragment();
            detailFragment.setArguments(args);

            HistoricFragment historicFragment = new HistoricFragment();
            historicFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, detailFragment)
                    .commit();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.historic_container, historicFragment)
                    .commit();


        } else {
            mTwoPane = false;
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_list, new CompanyListFragment())
                    .commit();
        }
        TrackMyStockSyncAdapter.initializeSyncAdapter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onItemSelected(String symbol) {
        if(mTwoPane) {

            Bundle args = new Bundle();
            args.putString(DetailActivity.SYMBOL, symbol);

            DetailFragment placeholderFragment = new DetailFragment();
            placeholderFragment.setArguments(args);

            HistoricFragment historicFragment = new HistoricFragment();
            historicFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, placeholderFragment)
                    .commit();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.historic_container, historicFragment)
                    .commit();

        } else {

            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.SYMBOL, symbol);
            startActivity(intent);

        }


    }

}

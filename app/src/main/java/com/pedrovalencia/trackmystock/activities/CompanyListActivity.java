package com.pedrovalencia.trackmystock.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.pedrovalencia.trackmystock.R;
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
            //TODO send the symbol
            mSymbol = getIntent().getStringExtra(DetailActivity.SYMBOL);

            Bundle args = new Bundle();
            args.putString(DetailActivity.NAME, getIntent().getStringExtra(DetailActivity.NAME));
            args.putString(DetailActivity.SYMBOL, mSymbol);
            args.putString(DetailActivity.LAST_UPDATE, getIntent().getStringExtra(DetailActivity.LAST_UPDATE));
            args.putDouble(DetailActivity.PRICE, getIntent().getDoubleExtra(DetailActivity.PRICE, 7.0));
            args.putDouble(DetailActivity.HIGH, getIntent().getDoubleExtra(DetailActivity.HIGH, 14.0));
            args.putDouble(DetailActivity.LOW, getIntent().getDoubleExtra(DetailActivity.LOW, 0.0));

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

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.company_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.action_add_company) {
            Intent intent = new Intent(this, AddCompanyActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onItemSelected(String name, String symbol, String lastUpdate,
                               Double price, Double high, Double low) {
        if(mTwoPane) {

            Bundle args = new Bundle();
            args.putString(DetailActivity.NAME, name);
            args.putString(DetailActivity.SYMBOL, symbol);
            args.putString(DetailActivity.LAST_UPDATE, lastUpdate);
            args.putDouble(DetailActivity.PRICE, price);
            args.putDouble(DetailActivity.HIGH, high);
            args.putDouble(DetailActivity.LOW, low);

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
            intent.putExtra(DetailActivity.NAME, name);
            intent.putExtra(DetailActivity.SYMBOL, symbol);
            intent.putExtra(DetailActivity.PRICE, price);
            intent.putExtra(DetailActivity.LAST_UPDATE, lastUpdate);
            intent.putExtra(DetailActivity.HIGH, high);
            intent.putExtra(DetailActivity.LOW, low);
            startActivity(intent);

        }


    }

}

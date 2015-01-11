package com.pedrovalencia.trackmystock.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.pedrovalencia.trackmystock.R;
import com.pedrovalencia.trackmystock.fragments.DetailFragment;
import com.pedrovalencia.trackmystock.fragments.HistoricFragment;

public class DetailActivity extends ActionBarActivity {

    public static final String SYMBOL = "symbol";

    private String mSymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mSymbol = getIntent().getStringExtra(SYMBOL);

        Bundle args = new Bundle();
        args.putString(SYMBOL, mSymbol);

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

    }

}

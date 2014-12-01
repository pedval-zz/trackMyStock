package com.pedrovalencia.trackmystock.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.pedrovalencia.trackmystock.R;
import com.pedrovalencia.trackmystock.data.CompanyContract;
import com.pedrovalencia.trackmystock.fragments.DetailFragment;
import com.pedrovalencia.trackmystock.fragments.HistoricFragment;

public class DetailActivity extends ActionBarActivity {

    public static final String NAME = "name";
    public static final String SYMBOL = "symbol";
    public static final String PRICE = "price";
    public static final String HIGH = "high";
    public static final String LOW = "low";
    public static final String LAST_UPDATE = "update";

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
                .add(R.id.detail_container, detailFragment)
                .commit();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.historic_container, historicFragment)
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_fragment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_discard_company) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage(R.string.dialog_message)
                    .setTitle(R.string.dialog_title);

            // Add the buttons
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Delete the company selected
                    getContentResolver().delete(
                            CompanyContract.CompanyEntry.buildCompanyUri(mSymbol),
                            null,
                            null);

                    Cursor cursor = getContentResolver().query(CompanyContract.CompanyEntry.CONTENT_URI, null, null, null, null);
                    Intent intent;
                    if(cursor.getCount() > 0) {
                        intent = new Intent(getApplicationContext(), CompanyListActivity.class);
                    } else {
                        intent = new Intent(getApplicationContext(), EmptyListActivity.class);
                    }
                    startActivity(intent);

                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog and we do nothing.
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

}

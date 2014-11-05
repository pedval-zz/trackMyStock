package com.pedrovalencia.trackmystock.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pedrovalencia.trackmystock.R;
import com.pedrovalencia.trackmystock.data.CompanyContract;
import com.pedrovalencia.trackmystock.fragments.HistoricFragment;

public class DetailActivity extends ActionBarActivity {

    public static final String NAME = "name";
    public static final String SYMBOL = "symbol";
    public static final String PRICE = "price";
    public static final String HIGH = "high";
    public static final String LOW = "low";
    public static final String LAST_UPDATE = "update";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {

            Bundle args = new Bundle();
            args.putString(NAME, getIntent().getStringExtra(NAME));
            args.putString(SYMBOL, getIntent().getStringExtra(SYMBOL));
            args.putString(LAST_UPDATE, getIntent().getStringExtra(LAST_UPDATE));
            args.putDouble(PRICE, getIntent().getDoubleExtra(PRICE, 0.0));
            args.putDouble(HIGH, getIntent().getDoubleExtra(HIGH, 0.0));
            args.putDouble(LOW, getIntent().getDoubleExtra(LOW, 0.0));

            PlaceholderFragment placeholderFragment = new PlaceholderFragment();
            placeholderFragment.setArguments(args);

            HistoricFragment historicFragment = new HistoricFragment();
            historicFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, placeholderFragment)
                    .commit();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_detail_container, historicFragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
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
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private String mSymbol;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            //Method that creates chart.
            //createChart(rootView);
            return rootView;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            mSymbol = getActivity().getIntent().getStringExtra(SYMBOL);
            setHasOptionsMenu(true);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            // Inflate the menu; this adds items to the action bar if it is present.
            inflater.inflate(R.menu.detail_fragment, menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();
            if (id == R.id.action_discard_company) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setMessage(R.string.dialog_message)
                        .setTitle(R.string.dialog_title);

                // Add the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Delete the company selected
                        getActivity().getContentResolver().delete(
                                CompanyContract.CompanyEntry.buildCompanyUri(mSymbol),
                                null,
                                null);

                        Cursor cursor = getActivity().getContentResolver().query(CompanyContract.CompanyEntry.CONTENT_URI, null, null, null, null);
                        Intent intent;
                        if(cursor.getCount() > 0) {
                            intent = new Intent(getActivity(), CompanyListActivity.class);
                        } else {
                            intent = new Intent(getActivity(), EmptyListActivity.class);
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


            return super.onOptionsItemSelected(item);
        }


        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Bundle args = getArguments();
            if(args != null && args.containsKey(DetailActivity.NAME)
                    && args.containsKey(DetailActivity.SYMBOL)
                    && args.containsKey(DetailActivity.LAST_UPDATE)
                    && args.containsKey(DetailActivity.PRICE)
                    && args.containsKey(DetailActivity.HIGH)
                    && args.containsKey(DetailActivity.LOW)
                    && args.containsKey(DetailActivity.PRICE)) {

                //Name (symbol) field
                TextView nameTextView = (TextView)getActivity().findViewById(R.id.detail_fragment_name_content);
                mSymbol = args.getString(DetailActivity.SYMBOL);
                nameTextView.setText(args.getString(DetailActivity.NAME) + " (" + mSymbol + ")");


                //Price
                TextView priceTextView = (TextView)getActivity().findViewById(R.id.detail_fragment_price_content);
                priceTextView.setText(String.valueOf(args.getDouble(DetailActivity.PRICE)));

                //Last update
                TextView updateTextView = (TextView)getActivity().findViewById(R.id.detail_fragment_date_content);
                updateTextView.setText(args.getString(DetailActivity.LAST_UPDATE));

                //High price
                TextView highTextView = (TextView)getActivity().findViewById(R.id.detail_fragment_high_content);
                highTextView.setText(String.valueOf(args.getDouble(DetailActivity.HIGH)));

                //Low price
                TextView lowTextView = (TextView)getActivity().findViewById(R.id.detail_fragment_low_content);
                lowTextView.setText(String.valueOf(args.getDouble(DetailActivity.LOW)));
            }
        }
    }
}

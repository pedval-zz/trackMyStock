package com.pedrovalencia.trackmystock.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pedrovalencia.trackmystock.R;
import com.pedrovalencia.trackmystock.activities.CompanyListActivity;
import com.pedrovalencia.trackmystock.activities.DetailActivity;
import com.pedrovalencia.trackmystock.activities.EmptyListActivity;
import com.pedrovalencia.trackmystock.activities.SettingsActivity;
import com.pedrovalencia.trackmystock.data.CompanyContract;

/**
 * Created by pedrovalencia on 30/11/14.
 */
public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private String mSymbol;

    private static final String SELECTED_KEY = "selected_key";

    private static final String[] DETAIL_COLUMNS = new String[] {
            CompanyContract.CompanyEntry.COLUMN_NAME,
            CompanyContract.CompanyEntry.COLUMN_SYMBOL,
            CompanyContract.CompanyEntry.COLUMN_LAST_UPDATE,
            CompanyContract.CompanyEntry.COLUMN_PRICE,
            CompanyContract.CompanyEntry.COLUMN_HIGH,
            CompanyContract.CompanyEntry.COLUMN_LOW
    };

    public DetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle args = getArguments();
        if(args != null && args.containsKey(DetailActivity.SYMBOL)) {
            getLoaderManager().restartLoader(0, null, this);
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        if(args != null && args.containsKey(DetailActivity.SYMBOL)) {
            getLoaderManager().initLoader(0, null, this);
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String symbol = getArguments().getString(DetailActivity.SYMBOL);
        return new CursorLoader(
                getActivity(),
                CompanyContract.CompanyEntry.buildCompanyUri(symbol),
                DETAIL_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        if (!cursor.moveToFirst()) { return; }

        //Name (symbol) field
        TextView nameTextView = (TextView)getView().findViewById(R.id.detail_fragment_name_content);
        mSymbol = cursor.getString(cursor.getColumnIndex(CompanyContract.CompanyEntry.COLUMN_SYMBOL));
        String name = cursor.getString(cursor.getColumnIndex(CompanyContract.CompanyEntry.COLUMN_NAME));
        nameTextView.setText(name + " (" + mSymbol + ")");


        //Price
        TextView priceTextView = (TextView)getView().findViewById(R.id.detail_fragment_price_content);
        Double price = cursor.getDouble(cursor.getColumnIndex(CompanyContract.CompanyEntry.COLUMN_PRICE));
        priceTextView.setText(String.valueOf(price));

        //Last update
        TextView updateTextView = (TextView)getView().findViewById(R.id.detail_fragment_date_content);
        String lastUpdate = cursor.getString(cursor.getColumnIndex(CompanyContract.CompanyEntry.COLUMN_LAST_UPDATE));
        updateTextView.setText(lastUpdate);

        //High price
        TextView highTextView = (TextView)getView().findViewById(R.id.detail_fragment_high_content);
        Double high = cursor.getDouble(cursor.getColumnIndex(CompanyContract.CompanyEntry.COLUMN_HIGH));
        highTextView.setText(String.valueOf(high));

        //Low price
        TextView lowTextView = (TextView)getView().findViewById(R.id.detail_fragment_low_content);
        Double low = cursor.getDouble(cursor.getColumnIndex(CompanyContract.CompanyEntry.COLUMN_LOW));
        lowTextView.setText(String.valueOf(low));

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
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
                        Bundle arg = new Bundle();
                        arg.putInt(SELECTED_KEY, 0);
                        intent = new Intent(getActivity().getApplicationContext(), CompanyListActivity.class);
                        intent.putExtras(arg);

                    } else {
                        intent = new Intent(getActivity().getApplicationContext(), EmptyListActivity.class);
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
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }
}


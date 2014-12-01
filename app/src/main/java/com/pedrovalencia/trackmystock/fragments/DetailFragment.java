package com.pedrovalencia.trackmystock.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pedrovalencia.trackmystock.R;
import com.pedrovalencia.trackmystock.activities.DetailActivity;
import com.pedrovalencia.trackmystock.data.CompanyContract;

/**
 * Created by pedrovalencia on 30/11/14.
 */
public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private String mSymbol;
    private String mName;
    private String mLastUpdate;
    private Double mPrice;
    private Double mHigh;
    private Double mLow;

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
        mName = cursor.getString(cursor.getColumnIndex(CompanyContract.CompanyEntry.COLUMN_NAME));
        nameTextView.setText(mName + " (" + mSymbol + ")");


        //Price
        TextView priceTextView = (TextView)getView().findViewById(R.id.detail_fragment_price_content);
        mPrice = cursor.getDouble(cursor.getColumnIndex(CompanyContract.CompanyEntry.COLUMN_PRICE));
        priceTextView.setText(String.valueOf(mPrice));

        //Last update
        TextView updateTextView = (TextView)getView().findViewById(R.id.detail_fragment_date_content);
        mLastUpdate = cursor.getString(cursor.getColumnIndex(CompanyContract.CompanyEntry.COLUMN_LAST_UPDATE));
        updateTextView.setText(mLastUpdate);

        //High price
        TextView highTextView = (TextView)getView().findViewById(R.id.detail_fragment_high_content);
        mHigh = cursor.getDouble(cursor.getColumnIndex(CompanyContract.CompanyEntry.COLUMN_HIGH));
        highTextView.setText(String.valueOf(mHigh));

        //Low price
        TextView lowTextView = (TextView)getView().findViewById(R.id.detail_fragment_low_content);
        mLow = cursor.getDouble(cursor.getColumnIndex(CompanyContract.CompanyEntry.COLUMN_LOW));
        lowTextView.setText(String.valueOf(mLow));

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        getLoaderManager().restartLoader(0, null, this);
    }
}


package com.pedrovalencia.trackmystock.fragments;

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
import android.widget.AdapterView;
import android.widget.ListView;

import com.pedrovalencia.trackmystock.R;
import com.pedrovalencia.trackmystock.activities.AddCompanyActivity;
import com.pedrovalencia.trackmystock.adapters.CompanyListAdapter;
import com.pedrovalencia.trackmystock.data.CompanyContract;

/**
 * Created by pedrovalencia on 29/11/14.
 */
public class CompanyListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private CompanyListAdapter companyListAdapter;
    private ListView mListView;
    private int mPosition;

    private static final String SELECTED_KEY = "selected_key";

    public interface Callback {
        public void onItemSelected(String name, String symbol, String lastUpdate,
                                   Double price, Double high, Double low);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private static final String[] COMPANY_COLUMNS = new String[] {
            CompanyContract.CompanyEntry.COLUMN_NAME,
            CompanyContract.CompanyEntry.COLUMN_SYMBOL
    };

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
    }

    public CompanyListFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if(mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_company_list, container, false);

        //Get a reference to the ListView and attach the adapter to it
        companyListAdapter = new CompanyListAdapter(getActivity(), null, 0);
        mListView = (ListView) rootView.findViewById(R.id.company_list_fragment_list_view);
        mListView.setAdapter(companyListAdapter);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //Attach onItemClickListener. This will be triggered each time the customer clicks on
        //one row
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Cursor cursor = companyListAdapter.getCursor();
                if (cursor != null && cursor.moveToPosition(position)) {

                    ((Callback)getActivity()).onItemSelected(cursor.getString(CompanyContract.CompanyEntry.COLUMN_NAME_POS),
                            cursor.getString(CompanyContract.CompanyEntry.COLUMN_SYMBOL_POS),
                            cursor.getString(CompanyContract.CompanyEntry.COLUMN_LAST_UPDATE_POS),
                            cursor.getDouble(CompanyContract.CompanyEntry.COLUMN_PRICE_POS),
                            cursor.getDouble(CompanyContract.CompanyEntry.COLUMN_HIGH_POS),
                            cursor.getDouble(CompanyContract.CompanyEntry.COLUMN_LOW_POS));

                }
                mPosition = position;
            }
        });

        if(savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

        //mListView.setItemChecked(mPosition, true);

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(
                getActivity(),
                CompanyContract.CompanyEntry.getCompany(),
                COMPANY_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.company_list, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.action_add_company) {
            Intent intent = new Intent(getActivity(), AddCompanyActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        companyListAdapter.swapCursor(cursor);
        if(mPosition != ListView.INVALID_POSITION) {
            mListView.smoothScrollToPosition(mPosition);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        companyListAdapter.swapCursor(null);
    }

}

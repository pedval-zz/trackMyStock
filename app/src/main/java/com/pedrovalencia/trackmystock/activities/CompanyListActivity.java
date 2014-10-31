package com.pedrovalencia.trackmystock.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pedrovalencia.trackmystock.R;
import com.pedrovalencia.trackmystock.adapters.CompanyListAdapter;
import com.pedrovalencia.trackmystock.data.CompanyContract;


public class CompanyListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
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
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

        private CompanyListAdapter companyListAdapter;
        private ListView mListView;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }

        private static final String[] COMPANY_COLUMNS = new String[] {
                CompanyContract.CompanyEntry.COLUMN_NAME,
                CompanyContract.CompanyEntry.COLUMN_CHANGE
        };

        @Override
        public void onResume() {
            super.onResume();
            getLoaderManager().restartLoader(0, null, this);
        }

        public PlaceholderFragment() {
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            getLoaderManager().initLoader(0, null, this);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_company_list, container, false);

            //Get a reference to the ListView and attach the adapter to it
            companyListAdapter = new CompanyListAdapter(getActivity(), null, 0);
            mListView = (ListView) rootView.findViewById(R.id.company_list_fragment_list_view);
            mListView.setAdapter(companyListAdapter);

            //Attach onItemClickListener. This will be triggered everytime the customer clicks on
            //one row
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //TODO send the needed info to DetailActivity to get the proper info
                }
            });

            return rootView;
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
            if (id == R.id.action_settings) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
            if(id == R.id.action_add_company) {
                Intent intent = new Intent(getActivity(), AddCompanyActivity.class);
                startActivity(intent);
            }
            return super.onOptionsItemSelected(item);
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
        public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
            companyListAdapter.swapCursor(cursor);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> cursorLoader) {
            companyListAdapter.swapCursor(null);
        }

    }
}

package com.pedrovalencia.trackmystock.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;

/**
 * Created by pedrovalencia on 20/10/14.
 */
public class CompanyAdapter extends ArrayAdapter<String> implements Filterable{

    private ArrayList<String> mResultList;
    private Filter mFilter;

    public CompanyAdapter(Context context, int resource) {
        super(context, resource);
        mResultList = new ArrayList<String>();
    }

    @Override
    public Filter getFilter() {
        mFilter = new Filter() {
            //Call the util that will fill the list of companies
            @Override
            protected FilterResults performFiltering(CharSequence query) {
                FilterResults filterResults = new FilterResults();
                if (query != null) {
                    // Retrieve the autocomplete results.
                    //TODO bring information dynamically
                    mResultList.add("Google");
                    mResultList.add("Yahoo");
                    mResultList.add("Microsoft");
                    mResultList.add("Nextub");
                    mResultList.add("TrackMyStock");

                    // Assign the data to the FilterResults
                    filterResults.values = mResultList;
                    filterResults.count = mResultList.size();
                }
                return filterResults;
            }

            //Once we have the result, notify to publish them
            @Override
            protected void publishResults(CharSequence query, FilterResults filterResults) {
                if (filterResults != null && filterResults.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return mFilter;
    }

    @Override
    public int getCount() {
        return mResultList.size();
    }
}

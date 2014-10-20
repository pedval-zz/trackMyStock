package com.pedrovalencia.trackmystock.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by pedrovalencia on 20/10/14.
 */
public class CompanyAdapter extends ArrayAdapter<String>{

    //TODO this list must be dynamic
    private static final String[] companyList = new String[] { "Google Inc.", "Yahoo Inc.", "Microsoft", "Nextub",
            "TrackMyStock"};

    public CompanyAdapter(Context context, int resource) {
        super(context, resource, companyList);
    }
}

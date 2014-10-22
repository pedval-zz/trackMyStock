package com.pedrovalencia.trackmystock.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pedrovalencia.trackmystock.R;

/**
 * Created by pedrovalencia on 22/10/14.
 */
public class CompanyListAdapter extends CursorAdapter{

    public CompanyListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        //Create new view that will contain the info retrieved at bindView
        View view = LayoutInflater.from(context).inflate(R.layout.single_company, viewGroup, false);
        return view;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //TODO Retreive the info from the Cursor

        //Name company field
        TextView nameCompany = (TextView)view.findViewById(R.id.single_company_name);
        nameCompany.setText("Nextub");

        //Percentage field
        //TODO format the TextView depends on the percentage value
        TextView percentage = (TextView)view.findViewById(R.id.single_company_percentage);
        percentage.setTextColor(context.getResources().getColor(R.color.green));
        percentage.setText("+34.5 %");

    }
}

package com.pedrovalencia.trackmystock.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pedrovalencia.trackmystock.R;
import com.pedrovalencia.trackmystock.data.CompanyContract;

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
        //Retrieve elements from cursor
        String name = cursor.getString(CompanyContract.CompanyEntry.COLUMN_NAME_POS);
        String change = cursor.getString(CompanyContract.CompanyEntry.COLUMN_CHANGE_POS);

        //Name company field
        TextView nameCompany = (TextView)view.findViewById(R.id.single_company_name);
        nameCompany.setText(name);

        //Change field
        TextView percentage = (TextView)view.findViewById(R.id.single_company_percentage);
        char sign = change.charAt(0);
        if(sign == '+') {
            percentage.setTextColor(context.getResources().getColor(R.color.green));
        } else if(sign == '-') {
            percentage.setTextColor(context.getResources().getColor(R.color.red));
        }
        percentage.setText(change);

    }
}

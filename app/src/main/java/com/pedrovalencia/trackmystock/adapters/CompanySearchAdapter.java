package com.pedrovalencia.trackmystock.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.pedrovalencia.trackmystock.R;
import com.pedrovalencia.trackmystock.activities.AddCompanyActivity;
import com.pedrovalencia.trackmystock.domain.CompanySignature;
import com.pedrovalencia.trackmystock.util.CompanySearchUtil;

import java.util.ArrayList;

/**
 * Created by pedrovalencia on 20/10/14.
 */
public class CompanySearchAdapter extends ArrayAdapter<String> implements Filterable{

    public Filter mFilter;
    private Context mContext;
    private int layoutResourceId;
    private ArrayList<CompanySignature> mResultList;


    public CompanySearchAdapter(Context context, int resource) {
        super(context, resource);
        this.mContext = context;
        this.layoutResourceId = resource;
        mResultList = new ArrayList<CompanySignature>();
    }

    @Override
    public Filter getFilter() {
        mFilter = new Filter() {
            //Call the util that will fill the list of companies
            @Override
            protected FilterResults performFiltering(CharSequence query) {
                FilterResults filterResults = new FilterResults();
                if (query != null) {
                    // Retrieve the auto complete results.
                    mResultList = CompanySearchUtil.getCompany(query.toString());

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
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            // inflate the layout
            LayoutInflater inflater = ((AddCompanyActivity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        // object item based on the position
        String data = "";
        if(mResultList != null && !mResultList.isEmpty()) {
            CompanySignature companySignature = mResultList.get(position);
            data = companySignature.getName() + "(" + companySignature.getSymbol() + ")";
        }

        // get the TextView and then set the text (item name) and tag (item ID) values
        TextView textViewItem = (TextView) convertView.findViewById(R.id.company_item_text_view);
        textViewItem.setText(data);

        return convertView;

    }

    @Override
    public int getCount() {
        return mResultList.size();
    }

    @Override
    public String getItem(int index){
        CompanySignature companySignature = mResultList.get(index);
        return companySignature.getName() + "(" + companySignature.getSymbol() + ")";
    }

}

package com.pedrovalencia.trackmystock.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewStyle;
import com.jjoe64.graphview.LineGraphView;
import com.pedrovalencia.trackmystock.R;
import com.pedrovalencia.trackmystock.activities.DetailActivity;
import com.pedrovalencia.trackmystock.domain.Historic;
import com.pedrovalencia.trackmystock.util.CompanySearchUtil;

import java.util.ArrayList;

/**
 * Created by pedrovalencia on 05/11/14.
 */
public class HistoricFragment extends Fragment {

    private String mSymbol;

    public HistoricFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_historic, container, false);
        mSymbol = getArguments().getString(DetailActivity.SYMBOL);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        //Call asynctask to retreive detail and store in database
        AsyncTask asyncTask = new RetrieveHistoric().execute(new String[]{mSymbol, CompanySearchUtil.getHistoricValue(getActivity())});
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private class RetrieveHistoric extends AsyncTask<String, Void, ArrayList<Historic>> {

        private ProgressDialog progress;

        @Override
        protected void onPostExecute(ArrayList<Historic> result) {
            super.onPostExecute(result);
            progress.dismiss();

            if(result != null && result.size() > 0) {

                GraphView.GraphViewData[] data = new GraphView.GraphViewData[result.size()];
                String[] dateArray = new String[result.size()];
                String historicMode = CompanySearchUtil.getHistoricValue(getActivity());
                //By default we will print date in x label (when week selected)
                int rangeXLabelDate = 2;
                if(historicMode.equals("month")) {
                    rangeXLabelDate = 5;
                }
                for (int i = 0; i < result.size(); i++) {
                    Double value = result.get(result.size() - 1 - i).getValue();
                    data[i] = new GraphView.GraphViewData(i, value);
                    if(i % rangeXLabelDate == 0) {
                        dateArray[i] = result.get(result.size() - 1 - i).getDate();
                    } else {
                        dateArray[i] = "";
                    }
                }

                // init example series data
                GraphViewSeries exampleSeries = new GraphViewSeries(getResources().getString(R.string.company_historic),
                        new GraphViewSeries.GraphViewSeriesStyle(getResources().getColor(R.color.main_text_color), 3), data);

                GraphView graphView = new LineGraphView(getActivity(), getResources().getString(R.string.company_historic));
                //Data
                graphView.addSeries(exampleSeries);
                //Remove grid
                graphView.getGraphViewStyle().setGridStyle(GraphViewStyle.GridStyle.NONE);
                //TODO
                graphView.setHorizontalLabels(dateArray);

                LinearLayout layout = (LinearLayout) (getView().findViewById(R.id.chart_layout));
                layout.addView(graphView);
            } else {

                LinearLayout layout = (LinearLayout) (getView().findViewById(R.id.chart_layout));

                TextView noResultTextView =new TextView(getActivity());
                noResultTextView.setLayoutParams(layout.getLayoutParams());
                noResultTextView.setText("No historic found");
                layout.addView(noResultTextView);
            }

        }

        @Override
        protected ArrayList<Historic> doInBackground(String... params) {
            // Bring info for this company
            ArrayList<Historic> historic = CompanySearchUtil.getHistoric(params[0], params[1]);

            return historic;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(getActivity());
            progress.setTitle(getActivity().getString(R.string.historic_loading));
            progress.setMessage(getActivity().getString(R.string.historic_please_wait));
            progress.show();
        }
    }
}



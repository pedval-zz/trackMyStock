package com.pedrovalencia.trackmystock.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewStyle;
import com.jjoe64.graphview.LineGraphView;
import com.pedrovalencia.trackmystock.R;

public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
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
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            //Method that creates chart.
            createChart(rootView);
            return rootView;
        }

        private void createChart(View rootView) {

            GraphView.GraphViewData[] data = new GraphView.GraphViewData[] {
                    new GraphView.GraphViewData(1, 2.0d)
                    , new GraphView.GraphViewData(2, 1.5d)
                    , new GraphView.GraphViewData(3, 2.5d)
                    , new GraphView.GraphViewData(4, 1.0d)
            };

            // init example series data
            GraphViewSeries exampleSeries = new GraphViewSeries(getActivity().getResources().getString(R.string.company_historic),
                    new GraphViewSeries.GraphViewSeriesStyle(getResources().getColor(R.color.main_text_color), 3),data);

            GraphView graphView = new LineGraphView(getActivity(), getActivity().getResources().getString(R.string.company_historic));
            //Data
            graphView.addSeries(exampleSeries);
            //Remove grid
            graphView.getGraphViewStyle().setGridStyle(GraphViewStyle.GridStyle.NONE);

            LinearLayout layout = (LinearLayout)(rootView.findViewById(R.id.chart_layout));
            layout.addView(graphView);
        }
    }
}

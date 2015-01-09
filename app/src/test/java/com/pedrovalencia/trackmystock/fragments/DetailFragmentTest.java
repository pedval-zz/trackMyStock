package com.pedrovalencia.trackmystock.fragments;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.pedrovalencia.trackmystock.R;
import com.pedrovalencia.trackmystock.activities.CompanyListActivity;
import com.pedrovalencia.trackmystock.activities.DetailActivity;
import com.pedrovalencia.trackmystock.data.CompanyContract;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;
import org.robolectric.util.FragmentTestUtil;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

/**
 * Created by valenciap on 07/01/2015.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DetailFragmentTest {

    private DetailFragment detailFragment;

    @Before
    public void setUp() throws Exception {
        detailFragment = new DetailFragment();
        FragmentTestUtil.startFragment(detailFragment);
    }

    @After
    public void tearDown() throws Exception {
        detailFragment.onDestroy();
    }

    @Test
    public void testFragmentElements() throws Exception {
        //Name
        TextView nameTextView = (TextView)detailFragment.getView().findViewById(R.id.detail_fragment_name_content);
        assertNotNull("Name field is null", nameTextView);

        //Price
        TextView priceTextView = (TextView)detailFragment.getView().findViewById(R.id.detail_fragment_price_content);
        assertNotNull("Price field is null", priceTextView);

        //Date
        TextView dateTextView = (TextView)detailFragment.getView().findViewById(R.id.detail_fragment_date_content);
        assertNotNull("Date field is null", dateTextView);

        //High price
        TextView highTextView = (TextView)detailFragment.getView().findViewById(R.id.detail_fragment_high_content);
        assertNotNull("High price field is null", highTextView);

        //Low price
        TextView lowTextView = (TextView)detailFragment.getView().findViewById(R.id.detail_fragment_low_content);
        assertNotNull("Low price field is null", lowTextView);

    }

    @Test
    @Config(qualifiers = "v10")
    public void testFragmentContainsGoodValues() throws Exception {

        ContentValues contentValues1 = getBasicContentValue();
        Bundle bundle = new Bundle();
        bundle.putString(DetailActivity.SYMBOL, "GOOG");

        DetailFragment detailFragment1 = new DetailFragment();
        detailFragment1.setArguments(bundle);
        FragmentTestUtil.startFragment(detailFragment1);
        detailFragment1.getActivity().getContentResolver().insert(CompanyContract.CompanyEntry.CONTENT_URI,
                contentValues1);


        detailFragment1.onResume();

        Bundle bundle1 = detailFragment1.getArguments();

        String code = bundle1.getString(DetailActivity.SYMBOL);

        assertTrue("Code is not correct: "+code, "GOOG".equals(code));

        //Name
        TextView nameTextView = (TextView)detailFragment1.getView().findViewById(R.id.detail_fragment_name_content);
        assertNotNull("Name field is null", nameTextView);
        assertTrue("Name is not the correct one: "+nameTextView.getText().toString(), "Google Inc. (GOOG)".equals(nameTextView.getText().toString()));

        //Price
        TextView priceTextView = (TextView)detailFragment1.getView().findViewById(R.id.detail_fragment_price_content);
        assertNotNull("Price field is null", priceTextView);
        assertTrue("Price is not the correct one: "+priceTextView.getText().toString(), "30.2".equals(priceTextView.getText().toString()));

        //Date
        TextView dateTextView = (TextView)detailFragment1.getView().findViewById(R.id.detail_fragment_date_content);
        assertNotNull("Date field is null", dateTextView);
        assertTrue("Date is not the correct one: "+dateTextView.getText().toString(), "10272014".equals(dateTextView.getText().toString()));

        //High price
        TextView highTextView = (TextView)detailFragment1.getView().findViewById(R.id.detail_fragment_high_content);
        assertNotNull("High price field is null", highTextView);
        assertTrue("High price is not the correct one: "+highTextView.getText().toString(), "32.15".equals(highTextView.getText().toString()));

        //Low price
        TextView lowTextView = (TextView)detailFragment1.getView().findViewById(R.id.detail_fragment_low_content);
        assertNotNull("Low price field is null", lowTextView);
        assertTrue("Low price is not the correct one: "+lowTextView.getText().toString(), "30.12".equals(lowTextView.getText().toString()));

    }

    private ContentValues getBasicContentValue() {
        //We first a element.
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(CompanyContract.CompanyEntry.COLUMN_SYMBOL, "GOOG");
        contentValues1.put(CompanyContract.CompanyEntry.COLUMN_NAME, "Google Inc.");
        contentValues1.put(CompanyContract.CompanyEntry.COLUMN_LAST_UPDATE, "10272014");
        contentValues1.put(CompanyContract.CompanyEntry.COLUMN_PRICE, 30.20);
        contentValues1.put(CompanyContract.CompanyEntry.COLUMN_HIGH, 32.15);
        contentValues1.put(CompanyContract.CompanyEntry.COLUMN_LOW, 30.12);
        contentValues1.put(CompanyContract.CompanyEntry.COLUMN_CHANGE, "+2.32");

        return contentValues1;
    }



}

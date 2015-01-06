package com.pedrovalencia.trackmystock.activities;

import android.app.Activity;

import com.pedrovalencia.trackmystock.data.CompanyContract;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static org.junit.Assert.assertTrue;

/**
 * Created by valenciap on 21/10/2014.
 */
@Config(emulateSdk=18)
@RunWith(RobolectricTestRunner.class)
public class CompanyListActivityTest {

    private ActivityController activityController;

    @Before
    public void setUp() throws Exception {
        activityController = Robolectric.buildActivity(CompanyListActivity.class).create();
    }

    @After
    public void tearDown() throws Exception {
        Activity activity = (Activity)activityController.get();
        activity.getContentResolver().delete(
                CompanyContract.CompanyEntry.CONTENT_URI,
                null,
                null);
        activityController.destroy();
    }

    @Test
    public void testActivityNotNull() throws Exception {
        Activity activity = (Activity)activityController.get();
        assertTrue("Activity is null", activity != null);
    }


    /*@Test
    public void testGoToDetailActivity() throws Exception {

        Activity activity = (Activity)activityController.get();

        //We first a element.

        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(CompanyContract.CompanyEntry.COLUMN_SYMBOL, "GOOG");
        contentValues1.put(CompanyContract.CompanyEntry.COLUMN_NAME, "Google Inc.");
        contentValues1.put(CompanyContract.CompanyEntry.COLUMN_LAST_UPDATE, "10272014");
        contentValues1.put(CompanyContract.CompanyEntry.COLUMN_PRICE, 30.20);
        contentValues1.put(CompanyContract.CompanyEntry.COLUMN_HIGH, 32.15);
        contentValues1.put(CompanyContract.CompanyEntry.COLUMN_LOW, 30.12);
        contentValues1.put(CompanyContract.CompanyEntry.COLUMN_CHANGE, "+2.32");

        activity.getContentResolver().insert(CompanyContract.CompanyEntry.CONTENT_URI,
                contentValues1);

        activity = (Activity)activityController.start().restart().resume().get();
        Fragment fragment = new CompanyListFragment();
        FragmentTestUtil.startFragment(fragment);

        ListView listView = (ListView)activity.findViewById(R.id.company_list_fragment_list_view);

        listView.performItemClick(null,0,0);

        //Test we move to next activity (DetailActivity) from onResume() event.
        Intent intent = Robolectric.shadowOf(activity).peekNextStartedActivity();
        assertTrue("Type of activity is not DetailActivity class: "+intent.getComponent().getClassName(),
                intent.getComponent().getClassName().equals(DetailActivity.class.getCanonicalName()));


    }*/

}

package com.pedrovalencia.trackmystock.fragments;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;

import android.view.MenuInflater;
import android.widget.ListView;

import com.pedrovalencia.trackmystock.R;
import com.pedrovalencia.trackmystock.activities.AddCompanyActivity;
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
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.tester.android.view.TestMenu;
import org.robolectric.tester.android.view.TestMenuItem;
import org.robolectric.util.ActivityController;
import org.robolectric.util.FragmentTestUtil;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

/**
 * Created by valenciap on 02/01/2015.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class CompanyListFragmentTest {

    private CompanyListFragment companyListFragment;

    @Before
    public void setUp() {
        companyListFragment = new CompanyListFragment();
        FragmentTestUtil.startFragment(companyListFragment);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void notNull() throws Exception {
        assertNotNull("Fragment is null", companyListFragment);
    }

    @Test
    public void testOnCreateOptionsMenu() throws Exception {

        companyListFragment.onResume();

        FragmentActivity fa = companyListFragment.getActivity();

        //Simulate a Menu object
        TestMenu testMenu = new TestMenu(Robolectric.application);
        new MenuInflater(Robolectric.application).inflate(R.menu.company_list, testMenu);

        fa.onCreateOptionsMenu(testMenu);

        //Test the first item is Settings
        TestMenuItem menuItem = (TestMenuItem)testMenu.getItem(0);
        assertTrue("There is not just one item in the menu", testMenu.size() == 1);
        assertTrue("First menu is not Add company: " + menuItem.getItemId(),
                menuItem.getItemId() == R.id.action_add_company);
    }

    @Test
    public void testWeHave2ElementsInList() throws Exception {

        //We first insert two elements.

        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(CompanyContract.CompanyEntry.COLUMN_SYMBOL, "GOOG");
        contentValues1.put(CompanyContract.CompanyEntry.COLUMN_NAME, "Google Inc.");
        contentValues1.put(CompanyContract.CompanyEntry.COLUMN_LAST_UPDATE, "10272014");
        contentValues1.put(CompanyContract.CompanyEntry.COLUMN_PRICE, 30.20);
        contentValues1.put(CompanyContract.CompanyEntry.COLUMN_HIGH, 32.15);
        contentValues1.put(CompanyContract.CompanyEntry.COLUMN_LOW, 30.12);
        contentValues1.put(CompanyContract.CompanyEntry.COLUMN_CHANGE, "+2.32");

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(CompanyContract.CompanyEntry.COLUMN_SYMBOL, "TRACKMY");
        contentValues2.put(CompanyContract.CompanyEntry.COLUMN_NAME, "TrackMyStock");
        contentValues2.put(CompanyContract.CompanyEntry.COLUMN_LAST_UPDATE, "10272014");
        contentValues2.put(CompanyContract.CompanyEntry.COLUMN_PRICE, 4.20);
        contentValues2.put(CompanyContract.CompanyEntry.COLUMN_HIGH, 5.15);
        contentValues2.put(CompanyContract.CompanyEntry.COLUMN_LOW, 2.12);
        contentValues2.put(CompanyContract.CompanyEntry.COLUMN_CHANGE, "-2.32");

        FragmentActivity fa = companyListFragment.getActivity();
        fa.getContentResolver().insert(CompanyContract.CompanyEntry.CONTENT_URI,
                contentValues1);
        fa.getContentResolver().insert(CompanyContract.CompanyEntry.CONTENT_URI,
                contentValues2);

        Cursor cursor =
                fa.getContentResolver().query(CompanyContract.CompanyEntry.CONTENT_URI, null, null, null, null);

        assertTrue("Cursor size does not match: "+ cursor.getCount(), cursor.getCount() == 2);
        //Then we start the activity

        companyListFragment.onResume();

        ListView listView = (ListView)companyListFragment.getView().findViewById(R.id.company_list_fragment_list_view);

        assertNotNull("listView is null", listView);

        cursor = (Cursor)listView.getItemAtPosition(0);

        assertTrue("Cursor does not have the proper number of elements",
                cursor.getCount() == 2);
    }

    @Test
    public void testGoToAddCompany() throws Exception {

        ActivityController<CompanyListActivity> activityController =
                Robolectric.buildActivity(CompanyListActivity.class).create();
        Activity activity = activityController.get();

        companyListFragment.onAttach(activity);

        //Simulate a Menu object
        TestMenu testMenu = new TestMenu(Robolectric.application);
        new MenuInflater(Robolectric.application).inflate(R.menu.company_list, testMenu);

        //Select item 1 (Add company)
        TestMenuItem menuItem = (TestMenuItem)testMenu.getItem(0);

        companyListFragment.onOptionsItemSelected(menuItem);

        //Test we move to AddCompanyActivity.
        ShadowActivity shadowActivity = Robolectric.shadowOf(activity);
        Intent intent = shadowActivity.peekNextStartedActivity();
        ShadowIntent shadowIntent = Robolectric.shadowOf(intent);

        assertTrue("Type of activity is not AddCompanyActivity class: " + shadowIntent.getComponent().getClassName(),
                shadowIntent.getComponent().getClassName().equals(AddCompanyActivity.class.getCanonicalName()));
    }

    @Test
    public void testElementsInFragment() throws Exception {

        companyListFragment.onResume();
        ListView view = (ListView)companyListFragment.getView().findViewById(R.id.company_list_fragment_list_view);
        assertTrue("There is no ListView in the Fragment", view != null);
    }

   /* @Test
    public void testGoToDetailActivity() throws Exception {

        //We first a element.

        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(CompanyContract.CompanyEntry.COLUMN_SYMBOL, "GOOG");
        contentValues1.put(CompanyContract.CompanyEntry.COLUMN_NAME, "Google Inc.");
        contentValues1.put(CompanyContract.CompanyEntry.COLUMN_LAST_UPDATE, "10272014");
        contentValues1.put(CompanyContract.CompanyEntry.COLUMN_PRICE, 30.20);
        contentValues1.put(CompanyContract.CompanyEntry.COLUMN_HIGH, 32.15);
        contentValues1.put(CompanyContract.CompanyEntry.COLUMN_LOW, 30.12);
        contentValues1.put(CompanyContract.CompanyEntry.COLUMN_CHANGE, "+2.32");

        ActivityController<CompanyListActivity> activityController =
                Robolectric.buildActivity(CompanyListActivity.class).create();
        Activity activity = activityController.get();
        companyListFragment.onAttach(activity);

        //FragmentActivity fa = companyListFragment.getActivity();
        activity.getContentResolver().insert(CompanyContract.CompanyEntry.CONTENT_URI,
                contentValues1);


        ListView listView = (ListView)companyListFragment.getView().findViewById(R.id.company_list_fragment_list_view);

        assertTrue("Class is: "+ listView.getClass().getSimpleName(), listView.getClass().getSimpleName().equals("ListView"));

        listView.performItemClick(null,0,0);

        //Test we move to next activity (DetailActivity) from onResume() event.
        Intent intent = Robolectric.shadowOf(activity).peekNextStartedActivity();
        assertTrue("Type of activity is not DetailActivity class: "+intent.getComponent().getClassName(),
                intent.getComponent().getClassName().equals(DetailActivity.class.getCanonicalName()));

    }*/







}

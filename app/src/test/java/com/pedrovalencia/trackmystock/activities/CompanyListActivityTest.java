package com.pedrovalencia.trackmystock.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.view.MenuInflater;
import android.view.SubMenu;
import android.widget.ListView;

import com.pedrovalencia.trackmystock.R;
import com.pedrovalencia.trackmystock.data.CompanyContract;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.tester.android.view.TestMenu;
import org.robolectric.tester.android.view.TestMenuItem;
import org.robolectric.util.ActivityController;
import org.robolectric.util.FragmentTestUtil;

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

    @Test
    public void testOnCreateOptionsMenu() throws Exception {

        Activity activity = (Activity)activityController.start().resume().get();

        //Simulate a Menu object
        TestMenu testMenu = new TestMenu(Robolectric.application);
        new MenuInflater(Robolectric.application).inflate(R.menu.company_list, testMenu);

        activity.onCreateOptionsMenu(testMenu);

        //Test the first item is Settings
        TestMenuItem menuItem = (TestMenuItem)testMenu.getItem(0);
        assertTrue("First menu is not Add company: " + menuItem.getItemId(),
                menuItem.getItemId() == R.id.action_add_company);

        //Test the second item is Settings
        menuItem = (TestMenuItem)testMenu.getItem(1);
        assertTrue("Second menu is not Settings: " + menuItem.getItemId(),
                menuItem.getItemId() == R.id.action_listActions);
    }

    @Test
    public void testGoToSettings() throws Exception {

        Activity activity = (Activity)activityController.start().resume().get();

        //Simulate a Menu object
        TestMenu testMenu = new TestMenu(Robolectric.application);
        new MenuInflater(Robolectric.application).inflate(R.menu.company_list, testMenu);

        //Select item 1 (Settings)
        TestMenuItem menuItem = (TestMenuItem)testMenu.getItem(1);

        SubMenu subMenu = menuItem.getSubMenu();
        TestMenuItem subMenuItem = (TestMenuItem)subMenu.getItem(0);

        activity.onOptionsItemSelected(subMenuItem);

        //Test we move to SettingsActivity.
        Intent intent = Robolectric.shadowOf(activity).peekNextStartedActivity();
        ShadowIntent shadowIntent = Robolectric.shadowOf(intent);

        assertTrue("Type of activity is not SettingsActivity class: " + shadowIntent.getComponent().getClassName(),
                shadowIntent.getComponent().getClassName().equals(SettingsActivity.class.getCanonicalName()));
    }

    @Test
    public void testGoToAddCompany() throws Exception {

        Activity activity = (Activity)activityController.start().resume().get();

        //Simulate a Menu object
        TestMenu testMenu = new TestMenu(Robolectric.application);
        new MenuInflater(Robolectric.application).inflate(R.menu.company_list, testMenu);

        //Select item 1 (Add company)
        TestMenuItem menuItem = (TestMenuItem)testMenu.getItem(0);

        activity.onOptionsItemSelected(menuItem);

        //Test we move to AddCompanyActivity.
        Intent intent = Robolectric.shadowOf(activity).peekNextStartedActivity();
        ShadowIntent shadowIntent = Robolectric.shadowOf(intent);

        assertTrue("Type of activity is not AddCompanyActivity class: " + shadowIntent.getComponent().getClassName(),
                shadowIntent.getComponent().getClassName().equals(AddCompanyActivity.class.getCanonicalName()));
    }

    @Test
    public void testElementsInFragment() throws Exception {

        Activity activity = (Activity)activityController.start().resume().get();
        ListView view = (ListView)activity.findViewById(R.id.company_list_fragment_list_view);
        assertTrue("There is no ListView in the Fragment", view != null);
    }

    @Test
    public void testWeHave2ElementsInList() throws Exception {

        Activity activity = (Activity)activityController.get();

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


        activity.getContentResolver().insert(CompanyContract.CompanyEntry.CONTENT_URI,
                contentValues1);
        activity.getContentResolver().insert(CompanyContract.CompanyEntry.CONTENT_URI,
                contentValues2);

        Cursor cursor =
                activity.getContentResolver().query(CompanyContract.CompanyEntry.CONTENT_URI, null, null, null, null);

        assertTrue("Cursor size does not match: "+ cursor.getCount(), cursor.getCount() == 2);
        //Then we start the activity
        activity = (Activity)activityController.start().restart().resume().get();
        Fragment fragment = new CompanyListActivity.PlaceholderFragment();
        FragmentTestUtil.startFragment(fragment);

        ListView listView = (ListView)activity.findViewById(R.id.company_list_fragment_list_view);

        cursor = (Cursor)listView.getItemAtPosition(0);

        assertTrue("Cursor does not have the proper number of elements",
                cursor.getCount() == 2);

    }

    @Test
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
        Fragment fragment = new CompanyListActivity.PlaceholderFragment();
        FragmentTestUtil.startFragment(fragment);

        ListView listView = (ListView)activity.findViewById(R.id.company_list_fragment_list_view);

        listView.performItemClick(null,0,0);

        //Test we move to next activity (DetailActivity) from onResume() event.
        Intent intent = Robolectric.shadowOf(activity).peekNextStartedActivity();
        assertTrue("Type of activity is not DetailActivity class: "+intent.getComponent().getClassName(),
                intent.getComponent().getClassName().equals(DetailActivity.class.getCanonicalName()));


    }

}

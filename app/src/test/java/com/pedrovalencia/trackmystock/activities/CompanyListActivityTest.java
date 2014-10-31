package com.pedrovalencia.trackmystock.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuInflater;
import android.widget.ListView;

import com.pedrovalencia.trackmystock.R;

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

import static org.junit.Assert.assertTrue;

/**
 * Created by valenciap on 21/10/2014.
 */
@Config(emulateSdk=18)
@RunWith(RobolectricTestRunner.class)
public class CompanyListActivityTest {

    private Activity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(CompanyListActivity.class).attach().create().start().resume().get();
    }

    @After
    public void tearDown() throws Exception {
        activity.finish();
    }

    @Test
    public void testActivityNotNull() throws Exception {
        assertTrue("Activity is null", activity != null);
    }

    @Test
    public void testOnCreateOptionsMenu() throws Exception {

        //Simulate a Menu object
        TestMenu testMenu = new TestMenu(Robolectric.application);
        new MenuInflater(Robolectric.application).inflate(R.menu.company_list, testMenu);

        activity.onCreateOptionsMenu(testMenu);

        //Test the first item is Settings
        TestMenuItem menuItem = (TestMenuItem)testMenu.getItem(0);
        assertTrue("First menu is not Settings: " + menuItem.getItemId(),
                menuItem.getItemId() == R.id.action_settings);
    }

    @Test
    public void testGoToSettings() throws Exception {

        //Simulate a Menu object
        TestMenu testMenu = new TestMenu(Robolectric.application);
        new MenuInflater(Robolectric.application).inflate(R.menu.company_list, testMenu);

        //Select item 0 (Settings)
        TestMenuItem menuItem = (TestMenuItem)testMenu.getItem(0);

        activity.onOptionsItemSelected(menuItem);

        //Test we move to SettingsActivity.
        Intent intent = Robolectric.shadowOf(activity).peekNextStartedActivity();
        ShadowIntent shadowIntent = Robolectric.shadowOf(intent);

        assertTrue("Type of activity is not SettingsActivity class: " + shadowIntent.getComponent().getClassName(),
                shadowIntent.getComponent().getClassName().equals(SettingsActivity.class.getCanonicalName()));
    }

    @Test
    public void testGoToAddCompany() throws Exception {

        //Simulate a Menu object
        TestMenu testMenu = new TestMenu(Robolectric.application);
        new MenuInflater(Robolectric.application).inflate(R.menu.company_list, testMenu);

        //Select item 1 (Add company)
        TestMenuItem menuItem = (TestMenuItem)testMenu.getItem(1);

        activity.onOptionsItemSelected(menuItem);

        //Test we move to SettingsActivity.
        Intent intent = Robolectric.shadowOf(activity).peekNextStartedActivity();
        ShadowIntent shadowIntent = Robolectric.shadowOf(intent);

        assertTrue("Type of activity is not AddCompanyActivity class: " + shadowIntent.getComponent().getClassName(),
                shadowIntent.getComponent().getClassName().equals(AddCompanyActivity.class.getCanonicalName()));
    }

    @Test
    public void testFragmentInActivity() throws Exception {
        CompanyListActivity.PlaceholderFragment fragment = new CompanyListActivity.PlaceholderFragment();
        assertTrue("Fragment is null", fragment != null);
    }

    @Test
    public void testElementsInFragment() throws Exception {
        ListView view = (ListView)activity.findViewById(R.id.company_list_fragment_list_view);
        assertTrue("There is no ListView in the Fragment", view != null);
    }

    //TODO navigation to previous activity
}

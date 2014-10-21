package com.pedrovalencia.trackmystock.activities;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import com.pedrovalencia.trackmystock.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
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
    public void testOnOptionsItemSelected() throws Exception {

        //Simulate a Menu object
        TestMenu testMenu = new TestMenu(Robolectric.application);
        new MenuInflater(Robolectric.application).inflate(R.menu.add_company, testMenu);

        TestMenuItem menuItem = (TestMenuItem)testMenu.getItem(0);

        //TODO test we go to SettingsActivity
        activity.onOptionsItemSelected(menuItem);
        menuItem.click();

        menuItem.setItemId(0);
        activity.onOptionsItemSelected(menuItem);
        menuItem.click();
    }

    @Test
    public void testFragmentInActivity() throws Exception {
        CompanyListActivity.PlaceholderFragment fragment = new CompanyListActivity.PlaceholderFragment();
        assertTrue("Fragment is null", fragment != null);
    }

    @Test
    public void testElementsInFragment() throws Exception {
        TextView view = (TextView)activity.findViewById(R.id.company_list_fragment_text_view);
        assertTrue("There is no TextView in the Fragment", view != null);
        assertTrue("TextView test does not match", view.getText().toString().equals("Hello world!"));
    }

    //TODO navigation to previous activity
}

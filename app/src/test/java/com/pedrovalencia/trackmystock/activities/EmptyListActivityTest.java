package com.pedrovalencia.trackmystock.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuInflater;
import android.view.SubMenu;
import android.widget.TextView;

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
import org.robolectric.util.ActivityController;

import static org.junit.Assert.assertTrue;

/**
 * Created by pedrovalencia on 17/10/14.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class EmptyListActivityTest {

    private ActivityController activityController;

    @Before
    public void setUp() throws Exception {
        activityController = Robolectric.buildActivity(EmptyListActivity.class).create();
    }

    @After
    public void tearDown() throws Exception {
        activityController.destroy();
    }

    @Test
    public void testActivityNotNull() throws Exception {
        Activity activity = (Activity)activityController.get();
        assertTrue("EmptyListActivity is null", activity != null);
    }

    @Test
    public void testOnCreateOptionsMenu() throws Exception {

        Activity activity = (Activity)activityController.get();

        //Simulate a Menu object
        TestMenu testMenu = new TestMenu(Robolectric.application);
        new MenuInflater(Robolectric.application).inflate(R.menu.empty_list, testMenu);

        activity.onCreateOptionsMenu(testMenu);

        //Test the first item is Settings
        TestMenuItem menuItem = (TestMenuItem)testMenu.getItem(0);
        assertTrue("First menu is not Settings: " + menuItem.getItemId(),
                menuItem.getItemId() == R.id.action_listActions);

    }

    @Test
    public void testOnOptionsItemSelected() throws Exception {

        Activity activity = (Activity)activityController.start().resume().get();
        //Simulate a Menu object
        TestMenu testMenu = new TestMenu(Robolectric.application);
        new MenuInflater(Robolectric.application).inflate(R.menu.empty_list, testMenu);

        TestMenuItem menuItem = (TestMenuItem)testMenu.getItem(0);

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
    public void testElementsInActivity() throws Exception {
        Activity activity = (Activity)activityController.get();

        //Main text testing
        TextView textView = (TextView)activity.findViewById(R.id.first_company_text);
        assertTrue("Main text view is null", textView != null);
        assertTrue("Main text does not match", textView.getText().toString().equals("Add your first company"));
        assertTrue("Main text color does not match",
                textView.getCurrentTextColor() == activity.getResources().getColor(R.color.grey_700));

        //Plus symbol testing
        TextView plusView = (TextView)activity.findViewById(R.id.plus_symbol_text);
        assertTrue("Plus symbol view is null", plusView != null);
        assertTrue("Plus symbol text does not match", plusView.getText().toString().equals("+"));
        assertTrue("Plus symbol color does not match",
                plusView.getCurrentTextColor() == activity.getResources().getColor(R.color.main_text_color));

    }

    @Test
    public void testPlusSymbolOnClick() throws Exception {
        Activity activity = (Activity)activityController.get();

        TextView plusView = (TextView)activity.findViewById(R.id.plus_symbol_text);
        plusView.performClick();

        Intent intent = Robolectric.shadowOf(activity).peekNextStartedActivity();
        assertTrue("Next activity is not AddCompanyActivity: "+intent.getComponent().getClassName(),
                intent.getComponent().getClassName().equals(AddCompanyActivity.class.getCanonicalName()));

    }

    //TODO navigation to previous activity

}

package com.pedrovalencia.trackmystock.activities;

import android.app.Activity;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.ListView;

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
import org.robolectric.util.ActivityController;

import static org.junit.Assert.assertTrue;

/**
 * Created by pedrovalencia on 18/10/14.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class AddCompanyActivityTest {

    private ActivityController activityController;

    @Before
    public void setUp() throws Exception {
        activityController = Robolectric.buildActivity(AddCompanyActivity.class).create();
    }

    @After
    public void tearDown() throws Exception {
        activityController.destroy();
    }

    @Test
    public void testActivityNotNull() throws Exception {
        Activity activity = (Activity)activityController.get();
        assertTrue("Activity is null", activity != null);
    }

    @Test
    public void testOnCreateOptionsMenu() throws Exception {

        Activity activity = (Activity)activityController.get();

        //Simulate a Menu object
        TestMenu testMenu = new TestMenu(Robolectric.application);
        new MenuInflater(Robolectric.application).inflate(R.menu.add_company, testMenu);

        activity.onCreateOptionsMenu(testMenu);

        //Test the first item is Settings
        TestMenuItem menuItem = (TestMenuItem)testMenu.getItem(0);
        assertTrue("First menu is not Settings: " + menuItem.getItemId(),
                menuItem.getItemId() == R.id.action_settings);

    }

    @Test
    public void testOnOptionsItemSelected() throws Exception {

        Activity activity = (Activity)activityController.get();
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
    public void testElements() throws Exception {
        Activity activity = (Activity)activityController.get();

        //EditText
        EditText editText = (EditText)activity.findViewById(R.id.add_company_edit_text);
        assertTrue("Edit Text is null",
                editText != null);
        assertTrue("Edit Text hint does not match: " + editText.getHint().toString(),
                editText.getHint().toString().equals("Company name"));
        assertTrue("Edit Text hint colour does not match: " + editText.getHintTextColors().getDefaultColor(),
                editText.getHintTextColors().getDefaultColor() == activity.getResources().getColor(R.color.grey_700));

        //ListView
        ListView listView = (ListView)activity.findViewById(R.id.add_company_list_view);
        assertTrue("List view is null", listView != null);
        assertTrue("List view length does not match (5): "+listView.getAdapter().getCount(),
                listView.getAdapter().getCount() == 5);
    }
}

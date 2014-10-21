package com.pedrovalencia.trackmystock.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuInflater;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
        AutoCompleteTextView textView = (AutoCompleteTextView)activity.findViewById(R.id.add_company_list_view);
        assertTrue("Edit Text is null",
                textView != null);
        assertTrue("Edit Text hint does not match: " + textView.getHint().toString(),
                textView.getHint().toString().equals("Company name"));
        assertTrue("Edit Text hint colour does not match: " + textView.getHintTextColors().getDefaultColor(),
                textView.getHintTextColors().getDefaultColor() == activity.getResources().getColor(R.color.grey_700));

        //Try to get the list
        textView.setText("Goo");
        //ListView
        assertTrue("Company list size does not match: (5): "+textView.getAdapter().getCount()
                , textView.getAdapter().getCount() == 5);

        //Test element in list
        assertTrue("Element in position 3 is not Nextub: "+((TextView)textView.getAdapter().getView(3, null, null)).getText().toString(),
                ((TextView)textView.getAdapter().getView(3, null, null)).getText().toString().equals("Nextub"));

        assertTrue("Element in position 3 is not Nextub: "+ textView.getAdapter().getItem(3),
                textView.getAdapter().getItem(3).equals("Nextub"));


        //TODO test when no results.

        //Button
        Button button = (Button)activity.findViewById(R.id.add_company_button);
        assertTrue("Button element is null", button != null);
        assertTrue("Button text does not match: "+button.getText().toString(),
                button.getText().toString().equals(activity.getResources().getString(R.string.add_company_accept_button)));

        //Test the button is disabled when no result
        textView.setText("");
        assertTrue("Button is not disabled", !button.isEnabled());

        //Test the button enabled when results
        textView.setText("Goo");
        textView.getOnItemClickListener().onItemClick(null, null, 0, 0);
        assertTrue("Button is not enabled", button.isEnabled());

    }

    @Test
    public void testAcceptButtonBehaviour() throws Exception {
        Activity activity = (Activity)activityController.get();

        Button button = (Button)activity.findViewById(R.id.add_company_button);
        button.performClick();

        Intent intent = Robolectric.shadowOf(activity).peekNextStartedActivity();
        assertTrue("Next activity is not CompanyListActivity: "+intent.getComponent().getClassName(),
                intent.getComponent().getClassName().equals(CompanyListActivity.class.getCanonicalName()));

        //TODO test when no results.
    }
}

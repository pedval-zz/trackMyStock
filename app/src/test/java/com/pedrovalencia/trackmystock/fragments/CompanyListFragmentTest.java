package com.pedrovalencia.trackmystock.fragments;

import android.content.ContentValues;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;

import com.pedrovalencia.trackmystock.R;
import com.pedrovalencia.trackmystock.activities.CompanyListActivity;
import com.pedrovalencia.trackmystock.data.CompanyContract;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.tester.android.view.TestMenu;
import org.robolectric.tester.android.view.TestMenuItem;
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
    private CompanyListActivity companyListActivity;

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
        assertTrue("First menu is not Add company: " + menuItem.getItemId(),
                menuItem.getItemId() == R.id.action_add_company);


    }

    /*@Test
    public void testWeHave2ElementsInList() throws Exception {

    }*/







}

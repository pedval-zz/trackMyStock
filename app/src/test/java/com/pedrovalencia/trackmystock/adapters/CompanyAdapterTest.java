package com.pedrovalencia.trackmystock.adapters;

import android.app.Activity;

import com.pedrovalencia.trackmystock.R;
import com.pedrovalencia.trackmystock.activities.AddCompanyActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertTrue;

/**
 * Created by pedrovalencia on 20/10/14.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class CompanyAdapterTest {

    @Test
    public void testCreationAdapter() {
        Activity activity = Robolectric.buildActivity(AddCompanyActivity.class).create().get();
        CompanyAdapter companyAdapter = new CompanyAdapter(activity, R.layout.simple_company_item);

        assertTrue("CompanyAdapter is null", companyAdapter != null);
    }
}

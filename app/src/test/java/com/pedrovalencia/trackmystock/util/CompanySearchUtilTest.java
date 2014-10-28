package com.pedrovalencia.trackmystock.util;

import com.pedrovalencia.trackmystock.domain.CompanySignature;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by pedrovalencia on 24/10/14.
 */
public class CompanySearchUtilTest {

    @Test
    public void testSearchGoodResult() throws Exception{
        ArrayList<CompanySignature> companyList = new ArrayList<CompanySignature>();
        assertNotNull("The company list is null", companyList);
        assertTrue("Company list size does not match (0): "+ companyList.size(),
                companyList.size() == 0);

    }

}

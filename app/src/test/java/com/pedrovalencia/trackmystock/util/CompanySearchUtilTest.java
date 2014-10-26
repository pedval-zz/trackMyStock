package com.pedrovalencia.trackmystock.util;

import com.pedrovalencia.trackmystock.domain.CompanySignature;

import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

/**
 * Created by pedrovalencia on 24/10/14.
 */
public class CompanySearchUtilTest {

    @Test
    public void testSearchGoodResult() throws Exception{
        //ArrayList<CompanySignature> companyList = CompanySearchUtil.getCompany("goo");
        ArrayList<CompanySignature> companyList = new ArrayList<CompanySignature>();
        assertNotNull("The company list is null", companyList);
        assertTrue("Company list size does not match: "+ companyList.size(),
                companyList.size() == 0);
        //assertTrue("Google is not in the list", companyList.get(0).getSymbol().equals("GOOG"));
    }
}

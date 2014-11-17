package com.pedrovalencia.trackmystock.domain;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by pedrovalencia on 16/11/14.
 */
public class CompanySignatureTest {

    @Test
    public void testToString() {
        CompanySignature companySignature = new CompanySignature("Google", "GOOG");
        assertTrue("CompanySignature toString does not match",
                companySignature.toString().equals("Google (GOOG)"));
    }
}

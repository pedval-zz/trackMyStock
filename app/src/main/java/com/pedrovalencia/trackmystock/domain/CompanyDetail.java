package com.pedrovalencia.trackmystock.domain;

/**
 * Created by pedrovalencia on 29/10/14.
 */
public class CompanyDetail {

    private CompanySignature companySignature;
    private Double price;
    private String date;
    private Double high;
    private Double low;
    private String change;

    public CompanyDetail(CompanySignature companySignatureIn, Double priceIn,
                         String dateIn, Double highIn, Double lowIn, String change) {
        this.companySignature = companySignatureIn;
        this.price = priceIn;
        this.date = dateIn;
        this.high = highIn;
        this.low = lowIn;
        this.change = change;
    }

    public CompanySignature getCompanySignature() {
        return companySignature;
    }

    public Double getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public Double getHigh() {
        return high;
    }

    public Double getLow() {
        return low;
    }

    public String getChange() {
        return change;
    }
}

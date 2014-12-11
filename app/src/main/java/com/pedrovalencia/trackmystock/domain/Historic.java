package com.pedrovalencia.trackmystock.domain;

/**
 * Created by pedrovalencia on 11/12/14.
 */
public class Historic {
    private Double value;
    private String date;

    public Historic (Double valueIn, String dateIn) {
        this.value = valueIn;
        this.date = dateIn;
    }

    public Double getValue() {
        return this.value;
    }

    public String getDate() {
        return this.date;
    }
}

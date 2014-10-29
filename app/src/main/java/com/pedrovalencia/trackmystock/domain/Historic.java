package com.pedrovalencia.trackmystock.domain;

/**
 * Created by pedrovalencia on 29/10/14.
 */
public class Historic {

    private String date;
    private Double close_value;

    public Historic(String dateIn, Double close_valueIn) {
        this.date = dateIn;
        this.close_value = close_valueIn;
    }
}

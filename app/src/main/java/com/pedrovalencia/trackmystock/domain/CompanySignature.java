package com.pedrovalencia.trackmystock.domain;

/**
 * Created by pedrovalencia on 24/10/14.
 */
public class CompanySignature {
    private String name;
    private String symbol;

    public CompanySignature(String nameIn, String symbolIn) {
        this.name = nameIn;
        this.symbol = symbolIn;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " (" + symbol + ")";
    }
}

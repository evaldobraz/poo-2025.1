package br.ufal.ic.p2.wepayu.services;

import br.ufal.ic.p2.wepayu.exceptions.NotANumberException;

import java.text.DecimalFormat;

public class NumberService {
    public static String formatAsCurrency(double number){
        DecimalFormat df = new DecimalFormat("###0.00");
        return df.format(number);
    }
    public static double formatAsDouble(String number) {
        String numberDouble = number.replace(",", ".");

        double numToReturn;
        try {
            numToReturn = Double.parseDouble(numberDouble);
        } catch (NumberFormatException e) {
            throw new NotANumberException("Not a number");
        }
        return numToReturn;
    }
}

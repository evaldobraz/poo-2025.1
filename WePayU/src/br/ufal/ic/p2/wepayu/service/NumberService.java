package br.ufal.ic.p2.wepayu.service;

import br.ufal.ic.p2.wepayu.exception.NotANumberException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

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

    public static String formatWithComma(double number) {
        Locale localeBrasil = new Locale("pt", "BR");

        NumberFormat formater = NumberFormat.getNumberInstance(localeBrasil);
        formater.setMaximumFractionDigits(2);
        formater.setMinimumFractionDigits(0);
        return formater.format(number);
    }

    public static double roundToTwoDecimals(double value) {
        // Retorna o valor arredondado para duas casas decimais
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.DOWN); // Usa regra padrão de arredondamento
        return bd.doubleValue();
    }
}

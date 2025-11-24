package br.ufal.ic.p2.wepayu.service;

import br.ufal.ic.p2.wepayu.exception.DataInvalidaException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateService {
    public static LocalDate stringToLocalDate(String date) throws Exception {

        if(!isAValidDateForTheMonth(date)) throw new DataInvalidaException(" ");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");

        return LocalDate.parse(date, formatter);
    }


    public static boolean isAValidDateForTheMonth(String date) throws Exception {

        String[] parts = date.split("/");

        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        if(month >= 1 && month <= 12 && day >= 1 && day <= 31) {
            YearMonth yearMonth = YearMonth.of(year, month);
            int monthLength = yearMonth.lengthOfMonth();
            return day <= monthLength;
        }

        return false;
    }

    public static boolean isLastBussinessDayOfMonth(LocalDate date) {
        YearMonth currentMonth = YearMonth.from(date);
        LocalDate lastDayOfMonth = currentMonth.atEndOfMonth();
        LocalDate lastBusinessDay = lastDayOfMonth;

        while (lastBusinessDay.getDayOfWeek() == DayOfWeek.SATURDAY ||
                lastBusinessDay.getDayOfWeek() == DayOfWeek.SUNDAY) {

            lastBusinessDay = lastBusinessDay.minusDays(1);
        }

        return date.isEqual(lastBusinessDay);
    }
}
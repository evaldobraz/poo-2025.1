package com.hotel.model.services;
import com.hotel.model.exceptions.InvalidDateException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateConverterService {
    private static final DateTimeFormatter BR_LOCAL_DATE  = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate convertStringToLocalDate(String date) {
        try {
            return LocalDate.parse(date, BR_LOCAL_DATE);
        }catch (DateTimeParseException e){
            throw new InvalidDateException("Invalid date format. Use dd/MM/yyyy.");
        }
    }

    public static String convertLocalDateToString(LocalDate localDate) {
        return localDate.format(BR_LOCAL_DATE);
    }


}

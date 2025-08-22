package com.hotel.model.entities;
import com.hotel.model.services.DateConverterService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservation {
    private final int roomNumber;
    private LocalDate checkInDate, checkOutDate;

    public Reservation(int roomNumber, LocalDate checkInDate, LocalDate checkOutDate) {
            this.roomNumber = roomNumber;
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void updateDates(LocalDate checkInDate, LocalDate checkOutDate) {
        setCheckInDate(checkInDate);
        setCheckOutDate(checkOutDate);
    }

    public int duration() {
        return (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }

    @Override
    public String toString() {
        String checkInFormat =  DateConverterService.convertLocalDateToString(this.checkInDate);
        String checkOutFormat =  DateConverterService.convertLocalDateToString(this.checkOutDate);

        return String.format("Reservation: Room " + getRoomNumber() + ", check-in: " + checkInFormat + ", check-out: " + checkOutFormat + ", " + duration() + " nights");
    }

}

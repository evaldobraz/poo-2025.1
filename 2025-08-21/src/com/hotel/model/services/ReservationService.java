package com.hotel.model.services;
import com.hotel.model.entities.Reservation;
import com.hotel.model.exceptions.InvalidReservationException;
import com.hotel.model.exceptions.PastDateException;


import java.time.LocalDate;


public class ReservationService {

    public ReservationService() {}

    public Reservation createReservation(int roomNumber, LocalDate checkInDate, LocalDate checkOutDate)
    {
        validateRoom(roomNumber);
        validateDates(checkInDate, checkOutDate);
        return new Reservation(roomNumber, checkInDate, checkOutDate);
    }

    public void updateReservation(Reservation reservation, LocalDate checkInDate, LocalDate checkOutDate) {
        validateDates(checkInDate, checkOutDate);
        reservation.updateDates(checkInDate, checkOutDate);
    }

    public void validateRoom(int roomNumber){
        if(roomNumber <= 0) throw new InvalidReservationException("Room number must be a positive integer");
    }

    public void validateDates(LocalDate checkInDate, LocalDate checkOutDate)
    {
        if(checkInDate.isBefore(LocalDate.now())) throw new PastDateException("Reservation dates for update must be future dates");
        if(checkOutDate.isBefore(checkInDate)) throw new InvalidReservationException("Check-out date must be after check-in date");
    }

}

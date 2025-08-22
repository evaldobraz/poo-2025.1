package com.hotel.model;

import com.hotel.model.entities.Reservation;
import com.hotel.model.exceptions.InvalidReservationException;
import com.hotel.model.exceptions.InvalidDateException;
import com.hotel.model.exceptions.PastDateException;
import com.hotel.model.services.DateConverterService;
import com.hotel.model.services.ReservationService;

import java.time.LocalDate;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        ReservationService rs = new ReservationService();
        Reservation reservation = null;

        try{
            System.out.print("Enter room number: ");
            int roomNumber = Integer.parseInt(input.nextLine());

            System.out.print("Enter check in date (dd/MM/yyyy): ");
            LocalDate checkInDate = DateConverterService.convertStringToLocalDate(input.next());
            System.out.print("Enter check out date (dd/MM/yyyy): ");
            LocalDate checkOutDate = DateConverterService.convertStringToLocalDate(input.next());

            reservation = rs.createReservation(roomNumber, checkInDate, checkOutDate);

            System.out.println(reservation);

            System.out.print("Enter data to update the reservation: ");

            System.out.print("Enter check in date (dd/MM/yyyy): ");
            checkInDate = DateConverterService.convertStringToLocalDate(input.next());
            System.out.print("Enter check out date (dd/MM/yyyy): ");
            checkOutDate = DateConverterService.convertStringToLocalDate(input.next());

            rs.updateReservation(reservation, checkInDate, checkOutDate);

            System.out.println(reservation);
        } catch (InvalidDateException | PastDateException | InvalidReservationException e) {
            System.err.println("Error in reservation: "+e.getMessage());
        }


        input.close();
    }
}

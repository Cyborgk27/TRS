package com.cepeda.trsv2.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TravelValidator {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isValidCodeTravel(String codeTravel) {
        // Validar que el código no sea nulo y tenga una longitud específica
        //, por ejemplo, 5 caracteres
        return codeTravel != null && codeTravel.length() == 5;
    }

    public static boolean isValidDestination(String destination) {
        // Validar que el destino no sea nulo y no esté vacío
        return destination != null && !destination.trim().isEmpty();
    }

    public static boolean isValidAvailablePlaces(String availablePlaces) {
        // Validar que los lugares disponibles sean un número entero positivo
        try {
            int places = Integer.parseInt(availablePlaces);
            return places > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidPrice(String price) {
        // Validar que el precio sea un número decimal positivo
        try {
            float priceValue = Float.parseFloat(price);
            return priceValue > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidTravelDate(String travelDate) {
        // Validar que la fecha de inicio de viaje esté en el formato correcto
        return isValidDate(travelDate);
    }

    public static boolean isValidReturnDate(String returnDate) {
        // Validar que la fecha de retorno de viaje esté en el formato correcto 
        //y sea después de la fecha de inicio
        return isValidDate(returnDate);
    }

    private static boolean isValidDate(String date) {
        // Validar que la fecha esté en el formato "yyyy-MM-dd HH:mm"
        try {
            LocalDateTime.parse(date, DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean areDatesValid(String travelDate, String returnDate) {
        // Validar que la fecha de retorno sea después de la fecha de inicio
        try {
            LocalDateTime startDate = LocalDateTime
                    .parse(travelDate, DATE_FORMATTER);
            LocalDateTime endDate = LocalDateTime
                    .parse(returnDate, DATE_FORMATTER);
            return !endDate.isBefore(startDate);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}

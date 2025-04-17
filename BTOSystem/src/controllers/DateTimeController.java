package controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
//Handles validation of data in datatime format
public class DateTimeController {
	static LocalDate currentDate = LocalDate.now();
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static boolean isAfter(String date1, String date2) {
        try {
            LocalDate d1 = LocalDate.parse(date1, FORMATTER);
            LocalDate d2 = LocalDate.parse(date2, FORMATTER);
            return d1.isAfter(d2);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidFormat(String dateStr) {
        return dateStr.matches("^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$");
    }
    
    public static boolean isAfter(String date)
    {
    	LocalDate inputDate = LocalDate.parse(date, FORMATTER);
    	 return inputDate.isAfter(currentDate);

    }
    
    public static boolean isActive(String d1, String d2)
    {
        LocalDate openingDate = LocalDate.parse(d1, FORMATTER);
        LocalDate closingDate = LocalDate.parse(d2, FORMATTER);
    	return (!currentDate.isBefore(openingDate) && !currentDate.isAfter(closingDate));
    }
}

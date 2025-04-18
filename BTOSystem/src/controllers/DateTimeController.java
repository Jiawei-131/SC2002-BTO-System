package controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
//Handles validation of data in datatime format
/**
 * This class provides utility methods for handling and validating dates.
 * It allows for comparison of dates, checking if a date is active within a given range, 
 * and verifying the format of a date string.
 * <p>
 * The class uses the `LocalDate` class from the Java standard library and a date 
 * format of "dd-MM-yyyy" for parsing and formatting dates.
 * </p>
 */
public class DateTimeController {
	static LocalDate currentDate = LocalDate.now();
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * Compares two dates in string format and checks if the first date is after the second date.
     * 
     * @param date1 The first date in "dd-MM-yyyy" format.
     * @param date2 The second date in "dd-MM-yyyy" format.
     * @return True if date1 is after date2, false otherwise or if there is an invalid format.
     */
    public static boolean isAfter(String date1, String date2) {
        try {
            LocalDate d1 = LocalDate.parse(date1, FORMATTER);
            LocalDate d2 = LocalDate.parse(date2, FORMATTER);
            return d1.isAfter(d2);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if the given date string matches the expected date format "dd-MM-yyyy".
     * 
     * @param dateStr The date string to validate.
     * @return True if the date matches the format, false otherwise.
     */
    public static boolean isValidFormat(String dateStr) {
        return dateStr.matches("^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$");
    }
    
    /**
     * Checks if the given date is after the current date.
     * 
     * @param date The date in "dd-MM-yyyy" format to compare.
     * @return True if the input date is after the current date, false otherwise.
     */
    public static boolean isAfter(String date)
    {
    	LocalDate inputDate = LocalDate.parse(date, FORMATTER);
    	 return inputDate.isAfter(currentDate);

    }
    
    /**
     * Checks if the current date is between the opening and closing dates.
     * 
     * @param d1 The opening date in "dd-MM-yyyy" format.
     * @param d2 The closing date in "dd-MM-yyyy" format.
     * @return True if the current date is between the opening and closing dates (inclusive), false otherwise.
     */
    public static boolean isActive(String d1, String d2)
    {
        LocalDate openingDate = LocalDate.parse(d1, FORMATTER);
        LocalDate closingDate = LocalDate.parse(d2, FORMATTER);
    	return (!currentDate.isBefore(openingDate) && !currentDate.isAfter(closingDate));
    }
}

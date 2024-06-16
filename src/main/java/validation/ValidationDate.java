package validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author CyborgK27
 */
public class ValidationDate {
    public static String converDateToString(java.util.Date returnDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(returnDate);
    }
    
    public static java.util.Date convertStringToDate(String dateString) throws ParseException{
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.parse(dateString);
    }
}

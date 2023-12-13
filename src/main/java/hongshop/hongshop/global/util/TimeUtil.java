package hongshop.hongshop.global.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    public static String nowDate(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM:ss"));
    }

    public static String daysAfter(int after){
        LocalDateTime futureDate = LocalDateTime.now().plusDays(after);
        return futureDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static boolean dateCompare(String futureDateString) {
        LocalDateTime futureDate = LocalDateTime.parse(futureDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime today = LocalDateTime.now();
        return futureDate.isAfter(today);
    }
}

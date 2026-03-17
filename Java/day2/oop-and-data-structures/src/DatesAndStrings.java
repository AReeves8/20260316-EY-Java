import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;

public class DatesAndStrings {

    public static void main(String[] args) {

        /**
         * StringBuilder is a class that proivides you with more ways to manipulate strings
         *      - methods is they provide a MUTABLE string buffer
         *          - regular String are IMMUTABLE
         */

        StringBuilder helloWorld = new StringBuilder("Hello World!");
        helloWorld.append(" My name is Austin!");

        // most string builder methods return "this" so you can chain them together
        helloWorld.append(" I am teaching java.")
            .replace(46, 51, "JAVA!!!")        // start is index, end is position
            .insert(5, ",");

        System.out.println(helloWorld);

        String substring = helloWorld.substring(47);    
        System.out.println(substring);
        
        String substring2 = new StringBuilder("0123456789").substring(3, 6);   // start is index, end is position
        System.out.println(substring2);

        String substring3 = new StringBuilder("0123456789").replace(3, 6, "X").toString(); // start is index, end is position
        System.out.println(substring3);


        /**
         * LocalDate, LocalTime, LocalDateTime
         *      - does not include timezones but will include dates and/or times
         *      - most methods return a new instance
         */

        LocalDate today = LocalDate.now();
        System.out.println(today.getMonth());
        System.out.println(today.getDayOfWeek());
        System.out.println(today.getDayOfMonth());
        System.out.println(today.getYear());

        // .of to create dates manually
        LocalDate aprilFools = LocalDate.of(2026, 4, 1);
        System.out.println(aprilFools.toString());

        // arithmetic of dates is very straightforward
        LocalDate aprilFools2025 = aprilFools.minusYears(1);
        System.out.println(aprilFools2025.toString());

        // Period is often used to measure difference in DATES
        Period period = Period.between(today, aprilFools);
        System.out.println("April Fools is " + period.getDays() +  " days away.");

        // Duration is used to measure the difference in TIMES
        Duration duration = Duration.between(LocalTime.now(), LocalTime.of(12, 0));
        System.out.println("12 O'Clock is " + duration.getSeconds() +  " seconds away.");
    }
}

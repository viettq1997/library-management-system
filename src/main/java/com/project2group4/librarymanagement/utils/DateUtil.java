package com.project2group4.librarymanagement.utils;

import java.sql.Date;
import java.time.LocalDate;

public class DateUtil {

    public static Date convertStringToDate(String date) {
        String[] dateArray = date.split("-");
        int year = Integer.parseInt(dateArray[0]);
        int month = Integer.parseInt(dateArray[1]);
        int day = Integer.parseInt(dateArray[2]);
        LocalDate localdate = LocalDate.of(year, month, day);

        return Date.valueOf(localdate);
    }
}

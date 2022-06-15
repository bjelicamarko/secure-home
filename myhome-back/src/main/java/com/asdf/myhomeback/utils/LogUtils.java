package com.asdf.myhomeback.utils;

import com.asdf.myhomeback.exceptions.DeviceException;
import com.asdf.myhomeback.exceptions.LogException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class LogUtils {

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

    public static void checkLogLevel(String selectedLevel) throws LogException {
        if(!(selectedLevel.equals("INFO") || selectedLevel.equals("WARN")  || selectedLevel.equals("ERROR") || selectedLevel.equals("FATAL") ||
                selectedLevel.equals("")))
            throw new LogException("Invalid log level.");
    }


    public static long checkStartDate(String startDate) throws LogException {
        if (startDate.equals(""))
            return Long.MIN_VALUE;
        try {
            LocalDate startDateLD = LocalDate.parse(startDate, formatter);
            return startDateLD.atStartOfDay(TimeZone.getDefault().toZoneId()).toInstant().toEpochMilli();
        } catch (Exception e) {
            throw new LogException("Invalid start date.");
        }
    }

    public static long checkEndDate(String endDate) throws LogException {
        if (endDate.equals(""))
            return Long.MAX_VALUE;
        try {
            LocalDate endDateLD = LocalDate.parse(endDate, formatter);
            endDateLD = endDateLD.plusDays(1); // pomjerimo za jedan dan
            return endDateLD.atStartOfDay(TimeZone.getDefault().toZoneId()).toInstant().toEpochMilli();
        } catch (Exception e) {
            throw new LogException("Invalid end date.");
        }
    }
}

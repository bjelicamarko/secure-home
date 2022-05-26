package com.asdf.myhomeback.utils;

import com.asdf.myhomeback.exceptions.AppUserException;
import com.asdf.myhomeback.exceptions.DeviceException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DeviceUtils {

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

    public static void checkMessageStatus(String selectedStatus) throws DeviceException {
        if(!(selectedStatus.equals("REGULAR") || selectedStatus.equals("WARNING")  ||
                selectedStatus.equals("PANIC") || selectedStatus.equals("")))
            throw new DeviceException("Invalid message status.");
    }


    public static long checkStartDate(String startDate) throws DeviceException {
        if (startDate.equals(""))
            return Long.MIN_VALUE;
        try {
            LocalDate startDateLD = LocalDate.parse(startDate, formatter);
            return startDateLD.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
        } catch (Exception e) {
            throw new DeviceException("Invalid start date.");
        }
    }

    public static long checkEndDate(String endDate) throws DeviceException {
        if (endDate.equals(""))
            return Long.MAX_VALUE;
        try {
            LocalDate endDateLD = LocalDate.parse(endDate, formatter);
            endDateLD = endDateLD.plusDays(1); // pomjerimo za jedan dan
            return endDateLD.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
        } catch (Exception e) {
            throw new DeviceException("Invalid end date.");
        }
    }
}

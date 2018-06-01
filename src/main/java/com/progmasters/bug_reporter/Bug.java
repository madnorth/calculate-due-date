package com.progmasters.bug_reporter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Bug {

    private static final DayOfWeek FIRST_WORKING_DAY = DayOfWeek.MONDAY;
    private static final DayOfWeek LAST_WORKING_DAY = DayOfWeek.FRIDAY;
    private static final LocalTime BEGINNING_OF_WORKING_TIME = LocalTime.of(9, 0);
    private static final LocalTime END_OF_WORKING_TIME = LocalTime.of(17, 0);

    public LocalDateTime calculateDueDate(LocalDateTime notificationTime, float turnaroundTimeInWorkingHour) {

        LocalDateTime dueDate;

        if (isWorkingDay(notificationTime) && isWorkingHour(notificationTime) && isValidTurnaroundTime(turnaroundTimeInWorkingHour)) {
            int days = getWorkingDays(turnaroundTimeInWorkingHour);
            float workingHour = turnaroundTimeInWorkingHour - days * 8;
            int minutes = (int) (workingHour * 60);

            LocalDate thisDay = notificationTime.toLocalDate();

            long minutesUntilEndOfWorkingTime = notificationTime.until(LocalDateTime.of(thisDay, END_OF_WORKING_TIME), ChronoUnit.MINUTES);

            LocalDateTime tempDueDate;
            if (minutes > minutesUntilEndOfWorkingTime) {
                long plusMinutesOnNextWorkingDay = minutes - minutesUntilEndOfWorkingTime;
                if (notificationTime.getDayOfWeek().getValue() == LAST_WORKING_DAY.getValue()) {
                    tempDueDate = LocalDateTime.of(thisDay.plusDays(3 + days), BEGINNING_OF_WORKING_TIME.plusMinutes(plusMinutesOnNextWorkingDay));
                } else {
                    tempDueDate = LocalDateTime.of(thisDay.plusDays(1 + days), BEGINNING_OF_WORKING_TIME.plusMinutes(plusMinutesOnNextWorkingDay));
                }
            } else {
                tempDueDate = notificationTime.plusMinutes(minutes).plusDays(days);
            }

            if (tempDueDate.getDayOfWeek().getValue() > LAST_WORKING_DAY.getValue()) {
                int plusDays = 8 - tempDueDate.getDayOfWeek().getValue();
                dueDate = tempDueDate.plusDays(plusDays);
            } else {
                dueDate = tempDueDate;
            }

        } else {
            throw new IllegalArgumentException();
        }

        return dueDate;
    }

    boolean isWorkingDay(LocalDateTime dateTime) {
        int numericValueOfDay = dateTime.getDayOfWeek().getValue();

        return (numericValueOfDay >= FIRST_WORKING_DAY.getValue() && numericValueOfDay <= LAST_WORKING_DAY.getValue());
    }

    boolean isWorkingHour(LocalDateTime dateTime) {
        LocalTime time = dateTime.toLocalTime();

        return ((time.isAfter(BEGINNING_OF_WORKING_TIME) || time.equals(BEGINNING_OF_WORKING_TIME)) && time.isBefore(END_OF_WORKING_TIME));
    }

    boolean isValidTurnaroundTime(float turnaroundTimeInWorkingHour) {
        return (turnaroundTimeInWorkingHour > 0);
    }

    int getWorkingDays(float turnaroundTimeInWorkingHour) {
        int turnaroundTimeInWorkingDays = 0;
        if (turnaroundTimeInWorkingHour > 8) {
            turnaroundTimeInWorkingDays = (int) turnaroundTimeInWorkingHour / 8;
        }

        return turnaroundTimeInWorkingDays;
    }
}

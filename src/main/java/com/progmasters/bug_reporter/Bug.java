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
            LocalDate thisDay = notificationTime.toLocalDate();
            int workingDays = getWorkingDays(turnaroundTimeInWorkingHour);
            int plusWeekendsDays = (workingDays / 5) * 2;
            float workingHour = turnaroundTimeInWorkingHour - workingDays * 8;

            int minutes = (int) (workingHour * 60);
            long minutesUntilEndOfWorkingTime = notificationTime.until(LocalDateTime.of(thisDay, END_OF_WORKING_TIME), ChronoUnit.MINUTES);

            if (minutes > minutesUntilEndOfWorkingTime) {
                long plusMinutesOnNextWorkingDay = minutes - minutesUntilEndOfWorkingTime;
                if (notificationTime.getDayOfWeek().getValue() == LAST_WORKING_DAY.getValue()) {
                    dueDate = LocalDateTime.of(thisDay.plusDays(3 + workingDays + plusWeekendsDays), BEGINNING_OF_WORKING_TIME.plusMinutes(plusMinutesOnNextWorkingDay));
                } else {
                    dueDate = LocalDateTime.of(thisDay.plusDays(1 + workingDays + plusWeekendsDays), BEGINNING_OF_WORKING_TIME.plusMinutes(plusMinutesOnNextWorkingDay));
                }
            } else {
                dueDate = notificationTime.plusMinutes(minutes).plusDays(workingDays + plusWeekendsDays);
            }
        } else {
            throw new IllegalArgumentException("Invalid date or turnaround time!");
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

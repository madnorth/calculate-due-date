package com.progmasters.bug_reporter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Bug {
    private LocalDateTime dueDate;
    private LocalDateTime notificationTime;
    private float turnaroundTimeInWorkingHour;

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(LocalDateTime notificationTime) {
        this.notificationTime = notificationTime;
    }

    public float getTurnaroundTimeInWorkingHour() {
        return turnaroundTimeInWorkingHour;
    }

    public void setTurnaroundTimeInWorkingHour(float turnaroundTimeInWorkingHour) {
        this.turnaroundTimeInWorkingHour = turnaroundTimeInWorkingHour;
    }

    public LocalDateTime calculateDueDate(LocalDateTime notificationTime, float turnaroundTimeInWorkingHour) {
        this.notificationTime = notificationTime;
        this.turnaroundTimeInWorkingHour = turnaroundTimeInWorkingHour;

        if (isWorkingDay() && isWorkingHour() && isValidTurnaroundTime()) {
            int days = getWorkingDays();
            float workingHour = this.turnaroundTimeInWorkingHour - days * 8;
            int minutes = (int) (workingHour * 60);

            LocalDate thisDay = this.notificationTime.toLocalDate();
            LocalTime endOfWorkingTime = LocalTime.of(17, 0);
            LocalTime beginningOfWorkingTime = LocalTime.of(9, 0);
            long minutesUntilEndOfWorkingTime = this.notificationTime.until(LocalDateTime.of(thisDay, endOfWorkingTime), ChronoUnit.MINUTES);

            LocalDateTime temp;
            if (minutes > minutesUntilEndOfWorkingTime) {
                long plusMinutesOnNextWorkingDay = minutesUntilEndOfWorkingTime - minutes;
                if (this.notificationTime.getDayOfWeek().getValue() == 5) {
                    temp = LocalDateTime.of(thisDay.plusDays(3 + days), beginningOfWorkingTime.plusMinutes(plusMinutesOnNextWorkingDay));
                } else {
                    temp = LocalDateTime.of(thisDay.plusDays(1 + days), beginningOfWorkingTime.plusMinutes(plusMinutesOnNextWorkingDay));
                }
            } else {
                temp = this.notificationTime.plusMinutes(minutes).plusDays(days);
            }

            if (temp.getDayOfWeek().getValue() == 6) {
                this.dueDate = temp.plusDays(2);
            } else if (temp.getDayOfWeek().getValue() == 7) {
                this.dueDate = temp.plusDays(1);
            } else {
                this.dueDate = temp;
            }

        } else {
            throw new IllegalArgumentException();
        }

        return this.dueDate;
    }

    public boolean isWorkingDay() {
        DayOfWeek day = notificationTime.getDayOfWeek();
        int numericValueOfDay = day.getValue();

        return (numericValueOfDay >= 1 && numericValueOfDay <= 5);
    }

    public boolean isWorkingHour() {
        int hour = notificationTime.getHour();

        return (hour >= 9 && hour < 16);
    }

    public boolean isValidTurnaroundTime() {
        return (turnaroundTimeInWorkingHour > 0);
    }

    public int getWorkingDays() {
        int turnaroundTimeInWorkingDays = 0;
        if (this.turnaroundTimeInWorkingHour > 8) {
            turnaroundTimeInWorkingDays = (int) this.turnaroundTimeInWorkingHour / 8;
        }

        return turnaroundTimeInWorkingDays;
    }
}

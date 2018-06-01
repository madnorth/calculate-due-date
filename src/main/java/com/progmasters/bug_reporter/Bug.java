package com.progmasters.bug_reporter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

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

    public LocalDateTime calculateDueDate(LocalDateTime notificationTime, float turnaroundTimeInWorkingHour) {
        this.notificationTime = notificationTime;
        this.turnaroundTimeInWorkingHour = turnaroundTimeInWorkingHour;

        if (isWorkingDay() && isWorkingHour() && isValidTurnaroundTime()) {

        } else {
            throw new IllegalArgumentException();
        }

        return null;
    }
}

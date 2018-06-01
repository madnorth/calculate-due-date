package com.progmasters.due_date;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class DueDate {
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
}

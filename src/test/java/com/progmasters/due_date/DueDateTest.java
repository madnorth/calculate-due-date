package com.progmasters.due_date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DueDateTest {

    private static final DateTimeFormatter TIME_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private DueDate workDay;
    private DueDate workDayAt5PM;
    private DueDate workDayAt9AM;

    @Before
    public void init() {
        workDay = new DueDate();
        workDay.setNotificationTime(LocalDateTime.parse("2018-06-01 11:11", TIME_PATTERN));
        workDayAt5PM = new DueDate();
        workDayAt5PM.setNotificationTime(LocalDateTime.parse("2018-06-01 17:00", TIME_PATTERN));
        workDayAt9AM = new DueDate();
        workDayAt9AM.setNotificationTime(LocalDateTime.parse("2018-06-01 09:00", TIME_PATTERN));
    }

    @Test
    public void testIfDateBetweenMondayAndFriday() {
        Assert.assertTrue(workDay.isWorkingDay());
    }

    @Test
    public void testIfDateNotBetweenMondayAndFriday() {
        DueDate weekend = new DueDate();
        weekend.setNotificationTime(LocalDateTime.parse("2018-05-26 11:11", TIME_PATTERN));
        Assert.assertFalse(weekend.isWorkingDay());
    }

    @Test
    public void testIfTimeBetween9AMAnd5PM() {
        Assert.assertTrue(workDay.isWorkingHour());
        Assert.assertTrue(workDayAt9AM.isWorkingHour());
    }

    @Test
    public void testIfTimeNotBetween9AMAnd5PM() {
        Assert.assertFalse(workDayAt5PM.isWorkingHour());
    }
}

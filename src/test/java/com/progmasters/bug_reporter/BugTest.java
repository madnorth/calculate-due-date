package com.progmasters.bug_reporter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BugTest {

    private static final DateTimeFormatter TIME_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private Bug workDayBug;
    private Bug workDayAt5PMBug;
    private Bug workDayAt9AMBug;

    @Before
    public void init() {
        workDayBug = new Bug();
        workDayAt5PMBug = new Bug();
        workDayAt9AMBug = new Bug();
    }

    @Test
    public void testIfDateBetweenMondayAndFriday() {
        workDayBug.setNotificationTime(LocalDateTime.parse("2018-06-01 11:11", TIME_PATTERN));
        Assert.assertTrue(workDayBug.isWorkingDay());
    }

    @Test
    public void testIfDateNotBetweenMondayAndFriday() {
        Bug weekend = new Bug();
        weekend.setNotificationTime(LocalDateTime.parse("2018-05-26 11:11", TIME_PATTERN));
        Assert.assertFalse(weekend.isWorkingDay());
    }

    @Test
    public void testIfTimeBetween9AMAnd5PM() {
        workDayBug.setNotificationTime(LocalDateTime.parse("2018-06-01 11:11", TIME_PATTERN));
        workDayAt9AMBug.setNotificationTime(LocalDateTime.parse("2018-06-01 09:00", TIME_PATTERN));
        Assert.assertTrue(workDayBug.isWorkingHour());
        Assert.assertTrue(workDayAt9AMBug.isWorkingHour());
    }

    @Test
    public void testIfTimeNotBetween9AMAnd5PM() {
        workDayAt5PMBug.setNotificationTime(LocalDateTime.parse("2018-06-01 17:00", TIME_PATTERN));
        Assert.assertFalse(workDayAt5PMBug.isWorkingHour());
    }

    @Test
    public void testIfTurnaroundTimeGreaterThanZero() {
        workDayBug.setTurnaroundTimeInWorkingHour(12);
        Assert.assertTrue(workDayBug.isValidTurnaroundTime());
    }

    @Test
    public void testIfTurnaroundTimeIsZero() {
        workDayBug.setTurnaroundTimeInWorkingHour(0);
        Assert.assertFalse(workDayBug.isValidTurnaroundTime());
    }

    @Test
    public void testIfTurnaroundTimeIsNegative() {
        workDayBug.setTurnaroundTimeInWorkingHour(-15);
        Assert.assertFalse(workDayBug.isValidTurnaroundTime());
    }

//    public void testIfCalculateDueDateGivenInvalidArgs(){
//        workDayBug.calculateDueDate();
//    }
}

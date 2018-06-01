package com.progmasters.bug_reporter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BugTest {

    private static final DateTimeFormatter TIME_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private Bug bug;
    private Bug workDayAt9AMBug;

    @Before
    public void init() {
        bug = new Bug();
        workDayAt9AMBug = new Bug();
    }

    @Test
    public void testIfDateBetweenMondayAndFriday() {
        bug.setNotificationTime(LocalDateTime.parse("2018-06-01 11:11", TIME_PATTERN));
        Assert.assertTrue(bug.isWorkingDay());
    }

    @Test
    public void testIfDateNotBetweenMondayAndFriday() {
        bug.setNotificationTime(LocalDateTime.parse("2018-05-26 11:11", TIME_PATTERN));
        Assert.assertFalse(bug.isWorkingDay());
    }

    @Test
    public void testIfTimeBetween9AMAnd5PM() {
        bug.setNotificationTime(LocalDateTime.parse("2018-06-01 11:11", TIME_PATTERN));
        workDayAt9AMBug.setNotificationTime(LocalDateTime.parse("2018-06-01 09:00", TIME_PATTERN));
        Assert.assertTrue(bug.isWorkingHour());
        Assert.assertTrue(workDayAt9AMBug.isWorkingHour());
    }

    @Test
    public void testIfTimeNotBetween9AMAnd5PM() {
        bug.setNotificationTime(LocalDateTime.parse("2018-06-01 17:00", TIME_PATTERN));
        Assert.assertFalse(bug.isWorkingHour());
    }

    @Test
    public void testIfTurnaroundTimeGreaterThanZero() {
        bug.setTurnaroundTimeInWorkingHour(12.0f);
        Assert.assertTrue(bug.isValidTurnaroundTime());
    }

    @Test
    public void testIfTurnaroundTimeIsZero() {
        bug.setTurnaroundTimeInWorkingHour(0.0f);
        Assert.assertFalse(bug.isValidTurnaroundTime());
    }

    @Test
    public void testIfTurnaroundTimeIsNegative() {
        bug.setTurnaroundTimeInWorkingHour(-15.0f);
        Assert.assertFalse(bug.isValidTurnaroundTime());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIfCalculateDueDateGivenInvalidTurnaroundTime() {
        bug.calculateDueDate(LocalDateTime.parse("2018-06-01 11:11", TIME_PATTERN), 0.0f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIfCalculateDueDateGivenInvalidTime() {
        bug.calculateDueDate(LocalDateTime.parse("2018-05-26 11:11", TIME_PATTERN), 2.0f);
    }
}

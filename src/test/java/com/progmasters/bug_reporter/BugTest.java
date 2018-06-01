package com.progmasters.bug_reporter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BugTest {

    private static final DateTimeFormatter TIME_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private Bug bug;

    @Before
    public void init() {
        bug = new Bug();
    }

    @Test
    public void testIfDateBetweenMondayAndFriday() {
        Assert.assertTrue(bug.isWorkingDay(LocalDateTime.parse("2018-06-01 11:11", TIME_PATTERN)));
    }

    @Test
    public void testIfDateNotBetweenMondayAndFriday() {
        Assert.assertFalse(bug.isWorkingDay(LocalDateTime.parse("2018-05-26 11:11", TIME_PATTERN)));
    }

    @Test
    public void testIfTimeBetween9AMAnd5PM() {
        Assert.assertTrue(bug.isWorkingHour(LocalDateTime.parse("2018-06-01 11:11", TIME_PATTERN)));
        Assert.assertTrue(bug.isWorkingHour(LocalDateTime.parse("2018-06-01 09:00", TIME_PATTERN)));
        Assert.assertTrue(bug.isWorkingHour(LocalDateTime.parse("2018-06-01 16:59", TIME_PATTERN)));
    }

    @Test
    public void testIfTimeNotBetween9AMAnd5PM() {
        Assert.assertFalse(bug.isWorkingHour(LocalDateTime.parse("2018-06-01 08:59", TIME_PATTERN)));
        Assert.assertFalse(bug.isWorkingHour(LocalDateTime.parse("2018-06-01 17:00", TIME_PATTERN)));
        Assert.assertFalse(bug.isWorkingHour(LocalDateTime.parse("2018-06-01 17:01", TIME_PATTERN)));
    }

    @Test
    public void testIfTurnaroundTimeGreaterThanZero() {
        Assert.assertTrue(bug.isValidTurnaroundTime(12.0f));
    }

    @Test
    public void testIfTurnaroundTimeIsZero() {
        Assert.assertFalse(bug.isValidTurnaroundTime(0.0f));
    }

    @Test
    public void testIfTurnaroundTimeIsNegative() {
        Assert.assertFalse(bug.isValidTurnaroundTime(-15.0f));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIfCalculateDueDateGivenInvalidTurnaroundTime() {
        bug.calculateDueDate(LocalDateTime.parse("2018-06-01 11:11", TIME_PATTERN), 0.0f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIfCalculateDueDateGivenInvalidTime() {
        bug.calculateDueDate(LocalDateTime.parse("2018-05-26 11:11", TIME_PATTERN), 2.0f);
    }

    @Test
    public void testCalculateWorkingDaysFromTurnaroundTime() {
        Assert.assertEquals(2, bug.getWorkingDays(16.0f));
    }

    @Test
    public void testCalculateDueDate() {
        Assert.assertEquals("2018-05-31 14:12", bug.calculateDueDate(LocalDateTime.parse("2018-05-29 14:12", TIME_PATTERN), 16).format(TIME_PATTERN));
        Assert.assertEquals("2018-06-04 10:51", bug.calculateDueDate(LocalDateTime.parse("2018-06-01 15:51", TIME_PATTERN), 3).format(TIME_PATTERN));
        Assert.assertEquals("2018-06-11 10:51", bug.calculateDueDate(LocalDateTime.parse("2018-06-01 15:51", TIME_PATTERN), 43).format(TIME_PATTERN));
        Assert.assertEquals("2018-06-05 09:06", bug.calculateDueDate(LocalDateTime.parse("2018-06-01 15:51", TIME_PATTERN), 9.25f).format(TIME_PATTERN));
        Assert.assertEquals("2018-06-01 16:51", bug.calculateDueDate(LocalDateTime.parse("2018-06-01 15:51", TIME_PATTERN), 1).format(TIME_PATTERN));
        Assert.assertEquals("2018-06-01 16:06", bug.calculateDueDate(LocalDateTime.parse("2018-06-01 15:51", TIME_PATTERN), 0.25f).format(TIME_PATTERN));
        Assert.assertEquals("2018-07-06 15:51", bug.calculateDueDate(LocalDateTime.parse("2018-06-01 15:51", TIME_PATTERN), 200).format(TIME_PATTERN));
    }
}

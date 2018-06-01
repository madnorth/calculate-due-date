package com.progmasters.due_date;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DueDateTest {

    private static final DateTimeFormatter TIME_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Test
    public void testIfDateBetweenMondayAndFriday() {
        DueDate friday = new DueDate();
        friday.setNotificationTime(LocalDateTime.parse("2018-06-01 11:11", TIME_PATTERN));
        Assert.assertTrue(friday.isWorkingDay());
    }

    @Test
    public void testIfDateNotBetweenMondayAndFriday() {
        DueDate saturday = new DueDate();
        saturday.setNotificationTime(LocalDateTime.parse("2018-05-26 11:11", TIME_PATTERN));
        Assert.assertFalse(saturday.isWorkingDay());
    }
}

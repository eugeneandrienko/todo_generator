package com.eugene_andrienko.test.todo_entries;

import com.eugene_andrienko.todo_entries.DateTimeEntry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for parser for DATETIME entries.
 */
@DisplayName("Tests for DATETIME parser.")
public class DateTimeEntryTest
{
    /**
     * Tests well-prepared DATETIME entry with date only.
     *
     * @throws ParseException Parsing failed.
     */
    @Test
    @DisplayName("Well-prepared DATETIME entry with date.")
    public void testParseDate() throws ParseException
    {
        final String dateString = "[20.05.2015] Test string.";
        DateTimeEntry entry = new DateTimeEntry(dateString);

        assertEquals("Test string.", entry.getText());
        assertTrue(new DateTimeFormatterBuilder()
                .parseStrict()
                .appendPattern("dd.MM.uuuu")
                .toFormatter()
                .withResolverStyle(ResolverStyle.STRICT)
                .toString()
                .equals(entry.getFormatter().toString()));
        assertTrue("20.05.2015".equals(
                entry.getDateTime().format(entry.getFormatter())));
    }

    /**
     * Tests well-prepared DATETIME entry with date and time.
     *
     * @throws ParseException Parsing failed.
     */
    @Test
    @DisplayName("Well-prepared DATETIME entry with date and time.")
    public void testParseDateTime() throws ParseException
    {
        final String dateTimeString = "[20.05.2015 22:33] Test string.";
        DateTimeEntry entry = new DateTimeEntry(dateTimeString);

        assertEquals("Test string.", entry.getText());
        assertTrue(new DateTimeFormatterBuilder()
                .parseStrict()
                .appendPattern("dd.MM.uuuu HH:mm")
                .toFormatter()
                .withResolverStyle(ResolverStyle.STRICT)
                .toString()
                .equals(entry.getFormatter().toString()));
        assertTrue("20.05.2015 22:33".equals(
                entry.getDateTime().format(entry.getFormatter())));
    }

    /**
     * Test parsing of wrong date string.
     */
    @Test
    @DisplayName("DATETIME entry with wrong date.")
    public void testParseWrongDate()
    {
        final String dateTimeString = "[22.04.xxx] Test string.";
        assertThrows(ParseException.class,
                () -> new DateTimeEntry(dateTimeString));
    }

    /**
     * Test parsing of wrong datetime string.
     */
    @Test
    @DisplayName("DATETIME entry with wrong date-time string.")
    public void testParseWrongDateTime()
    {
        final String dateTimeString = "[22.05.2015 22:xs] Test string.";
        assertThrows(ParseException.class,
                () -> new DateTimeEntry(dateTimeString));
    }

    /**
     * Test parsing entry without date-time section.
     */
    @Test
    @DisplayName("Test without DATETIME section.")
    public void testParseEmptyDateTimeSection()
    {
        final String entry = "test string";
        assertThrows(ParseException.class, () -> new DateTimeEntry(entry));
    }

    /**
     * Test parsing entry without text
     */
    @Test
    @DisplayName("DATETIME entry without any valuable text.")
    public void testParseEmptyText()
    {
        final String entry = "[22.05.2015 22:01]";
        assertThrows(ParseException.class, () -> new DateTimeEntry(entry));
    }

    /**
     * Test parsing empty string.
     */
    @Test
    @DisplayName("Empty string.")
    public void testParseEmptyString()
    {
        final String entry = "";
        assertThrows(ParseException.class, () -> new DateTimeEntry(entry));
    }
}

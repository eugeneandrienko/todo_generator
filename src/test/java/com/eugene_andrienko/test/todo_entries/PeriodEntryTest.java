package com.eugene_andrienko.test.todo_entries;

import com.eugene_andrienko.todo_entries.PeriodEntry;
import java.text.ParseException;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Test for parser of PERIOD entries.
 */
@DisplayName("Tests for PERIOD parser.")
public class PeriodEntryTest
{
    /**
     * Tests well-prepared PERIOD entry.
     *
     * @throws ParseException Parsing failed.
     */
    @Test
    @DisplayName("Well-prepared PERIOD entry")
    public void testParsePeriod() throws ParseException
    {
        final String periodString = "[20.05.2015-21.05.2015] Test string.";
        PeriodEntry entry = new PeriodEntry(periodString);

        assertEquals("Test string.", entry.getText());
        assertTrue(new DateTimeFormatterBuilder()
                .parseStrict()
                .appendPattern("dd.MM.uuuu")
                .toFormatter()
                .withResolverStyle(ResolverStyle.STRICT)
                .toString()
                .equals(entry.getFormatter().toString()));
        assertTrue("20.05.2015".equals(
                entry.getDateStart().format(entry.getFormatter())));
        assertTrue("21.05.2015".equals(
                entry.getDateEnd().format(entry.getFormatter())));
    }

    /**
     * Test parsing of wrong date string.
     */
    @Test
    @DisplayName("PERIOD entry with wrong date.")
    public void testParseWrongPeriod()
    {
        final String periodStringStart = "[22.04.xxx-22.04.2019] Test string.";
        final String periodStringEnd = "[22.04.2019-22.04.xxx] Test string.";
        assertThrows(ParseException.class,
                () -> new PeriodEntry(periodStringStart));
        assertThrows(ParseException.class,
                () -> new PeriodEntry(periodStringEnd));
    }

    /**
     * Test parsing entry without date section.
     */
    @Test
    @DisplayName("Test without PERIOD section.")
    public void testParseEmptyPeriodSection()
    {
        final String entry = "test string";
        assertThrows(ParseException.class, () -> new PeriodEntry(entry));
    }

    /**
     * Test parsing entry without text
     */
    @Test
    @DisplayName("PERIOD entry without any valuable text.")
    public void testParseEmptyText()
    {
        final String entry = "[22.05.2015-23.05.2015]";
        assertThrows(ParseException.class, () -> new PeriodEntry(entry));
    }

    /**
     * Test parsing empty string.
     */
    @Test
    @DisplayName("Empty string.")
    public void testParseEmptyString()
    {
        final String entry = "";
        assertThrows(ParseException.class, () -> new PeriodEntry(entry));
    }

    /**
     * Test parsing entry with equal dates in period.
     */
    @Test
    @DisplayName("PERIOD entry with equal dates.")
    public void testParseEqualsDates()
    {
        final String periodString = "[07.06.2019-07.06.2019] Test string.";
        assertThrows(ParseException.class,
                () -> new PeriodEntry(periodString));
    }

    /**
     * Test parsing entry with start date bigger than end date.
     */
    @Test
    @DisplayName("PERIOD entry with reversed dates order.")
    public void testParseStartDateBiggerThanEndDate()
    {
        final String periodString = "[07.06.2019-01.06.2019] Test string.";
        assertThrows(ParseException.class,
                () -> new PeriodEntry(periodString));
    }
}

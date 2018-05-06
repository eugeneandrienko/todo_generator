package com.eugene_andrienko.test;

import com.eugene_andrienko.Parser;
import com.eugene_andrienko.todo_entries.AbstractEntry;
import com.eugene_andrienko.todo_entries.DateTimeEntry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests overall parser.
 */
@DisplayName("Tests for main parser.")
public class ParserTest
{
    /**
     * Tests parsing of TO-DO and other strings.
     */
    @Test
    @DisplayName("Valid strings.")
    public void testParseValidStrings()
    {
        Parser parser = new Parser();
        LinkedList<AbstractEntry> results = parser.parse(prepareTestList(
                "- Test1",
                "- Test2",
                "~ Note",
                "[25.06.2015] Date test"));
        assertEquals("Test1", results.get(0).getText());
        assertEquals("Test2", results.get(1).getText());
        assertEquals("Note", results.get(2).getText());
        assertEquals("Date test", results.get(3).getText());
        DateTimeEntry entry = (DateTimeEntry)results.get(3);
        assertEquals("25.06.2015",
                entry.getDateTime().format(entry.getFormatter()));
    }

    /**
     * Tests parsings set of strings with one wrong string.
     */
    @Test
    @DisplayName("One invalid string.")
    public void testParseInvalidString()
    {
        Parser parser = new Parser();
        LinkedList<AbstractEntry> results = parser.parse(prepareTestList(
                "- Test1",
                "Test2"));
        assertEquals("Test1", results.get(0).getText());
        assertEquals(1, results.size(), "Size of results should be 1");
    }

    /**
     * Parsing list with all invalid strings.
     */
    @Test
    @DisplayName("All invalid strings.")
    public void testParseInvalidStrings()
    {
        Parser parser = new Parser();
        LinkedList<AbstractEntry> results = parser.parse(prepareTestList(
                "Test1",
                "Test2"));
        assertEquals(0, results.size(), "Size of results should be 0");
    }

    /**
     * Parsing empty list.
     */
    @Test
    @DisplayName("Empty set of strings.")
    public void testParseEmptyList()
    {
        Parser parser = new Parser();
        LinkedList<AbstractEntry> results = parser.parse(prepareTestList());
        assertEquals(0, results.size(), "Size of results should be 0");
    }

    /**
     * Prepare list of strings for test.
     *
     * @param strings Strings for test.
     * @return Stream for parser.
     */
    private Stream<String> prepareTestList(String ... strings)
    {
        return Arrays.stream(strings);
    }
}

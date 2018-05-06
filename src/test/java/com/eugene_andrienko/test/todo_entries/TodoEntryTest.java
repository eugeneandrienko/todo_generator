package com.eugene_andrienko.test.todo_entries;

import com.eugene_andrienko.todo_entries.TodoEntry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests parser for TO DO entries.
 */
@DisplayName("Tests for TODO parser.")
public class TodoEntryTest
{
    /**
     * Tests well-prepared TO DO entry.
     *
     * @throws ParseException Parsing of well-prepared entry is failed.
     */
    @Test
    @DisplayName("Well prepared TODO entry.")
    public void testParse() throws ParseException
    {
        final String todoString1 = "- test TODO string.";
        final String todoString2 = "-        test TODO string.";
        final String expectedResult = "test TODO string.";

        TodoEntry testObject = new TodoEntry(todoString1);
        assertEquals(expectedResult, testObject.getText(), expectedResult);

        testObject = new TodoEntry(todoString2);
        assertEquals(expectedResult, testObject.getText(), expectedResult);
    }

    /**
     * Tests parsing of empty (wrong!) TO DO entry.
     */
    @Test
    @DisplayName("TODO entry without text.")
    public void testParseOnlyToDoMark()
    {
        final String todoString = "-";
        assertThrows(ParseException.class, () -> new TodoEntry(todoString));
    }

    /**
     * Tests parsing of TO DO entry without "-" mark (wrong!).
     */
    @Test
    @DisplayName("Text without TODO entry mark.")
    public void testParseOnlyText()
    {
        final String todoString = "test TODO string";
        assertThrows(ParseException.class, () -> new TodoEntry(todoString));
    }

    /**
     * Tests parsing of empty string.
     */
    @Test
    @DisplayName("Empty string.")
    public void testParseEmptyString()
    {
        final String todoEmptyString = "";
        assertThrows(ParseException.class,
                () -> new TodoEntry(todoEmptyString));
    }
}

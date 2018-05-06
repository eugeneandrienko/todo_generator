package com.eugene_andrienko.test.todo_entries;

import com.eugene_andrienko.todo_entries.NoteEntry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests parser for note entries.
 */
@DisplayName("Tests for note entry parser.")
public class NoteEntryTest
{
    /**
     * Tests well-prepared note entry.
     *
     * @throws ParseException Parsing of well-prepared entry is failed.
     */
    @Test
    @DisplayName("Well prepared NOTE entry.")
    public void testParse() throws ParseException
    {
        final String noteString1 = "~ test string.";
        final String noteString2 = "~        test string.";
        final String expectedResult = "test string.";

        NoteEntry testObject = new NoteEntry(noteString1);
        assertEquals(expectedResult, testObject.getText(), expectedResult);

        testObject = new NoteEntry(noteString2);
        assertEquals(expectedResult, testObject.getText(), expectedResult);
    }

    /**
     * Tests parsing of empty (wrong!) note entry.
     */
    @Test
    @DisplayName("NOTE entry without text.")
    public void testParseOnlyNoteMark()
    {
        final String noteString = "~";
        assertThrows(ParseException.class, () -> new NoteEntry(noteString));
    }

    /**
     * Tests parsing of note entry without "~" mark (wrong!).
     */
    @Test
    @DisplayName("Text without note entry mark.")
    public void testParseOnlyText()
    {
        final String noteString = "test string";
        assertThrows(ParseException.class, () -> new NoteEntry(noteString));
    }

    /**
     * Tests parsing of empty string.
     */
    @Test
    @DisplayName("Empty string.")
    public void testParseEmptyString()
    {
        final String emptyString = "";
        assertThrows(ParseException.class, () -> new NoteEntry(emptyString));
    }
}

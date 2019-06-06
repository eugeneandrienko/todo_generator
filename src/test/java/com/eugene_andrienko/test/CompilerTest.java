package com.eugene_andrienko.test;

import com.eugene_andrienko.Compiler;
import com.eugene_andrienko.todo_entries.AbstractEntry;
import com.eugene_andrienko.todo_entries.DateTimeEntry;
import com.eugene_andrienko.todo_entries.NoteEntry;
import com.eugene_andrienko.todo_entries.PeriodEntry;
import com.eugene_andrienko.todo_entries.TodoEntry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Test for compiler of TeX entries.
 */
@DisplayName("Tests for TeX compiler.")
public class CompilerTest
{
    /**
     * Tests TO-DO entry generation.
     *
     * @throws ParseException Failed to parse test string.
     */
    @Test
    @DisplayName("Test TODO entry generation.")
    public void testTodoEntryGeneration() throws ParseException
    {
        LinkedList<AbstractEntry> entries = new LinkedList<>();
        entries.add(new TodoEntry("- Test"));
        Compiler compiler = new Compiler();
        Stream<String> result = compiler.compile(entries);
        Optional<String> string = result.findFirst();
        string.ifPresent(entry -> assertEquals("\\item Test", entry,
                "Wrong TeX generated"));
        if(!string.isPresent())
        {
            assertTrue(false, "No resulting string");
        }
    }

    /**
     * Tests DATETIME entry generation.
     *
     * @throws ParseException Failed to parse test string.
     */
    @Test
    @DisplayName("Test DATETIME entry generation.")
    public void testDateTimeEntryGeneration() throws ParseException
    {
        LinkedList<AbstractEntry> entries = new LinkedList<>();
        entries.add(new DateTimeEntry("[25.05.2016] Test text"));
        Compiler compiler = new Compiler();
        Stream<String> result = compiler.compile(entries);
        Optional<String> string = result.findFirst();
        string.ifPresent(entry -> assertEquals(
                "\\item[\\textbigcircle] [25.05.2016] Test text",
                entry,
                "Wrong TeX generated"));
        if(!string.isPresent())
        {
            assertTrue(false, "No resulting string");
        }
    }

    /**
     * Tests PERIOD entry generation.
     *
     * @throws ParseException Failed to parse test string.
     */
    @Test
    @DisplayName("Test PERIOD entry generation.")
    public void testPeriodEntryGeneration() throws ParseException
    {
        LinkedList<AbstractEntry> entries = new LinkedList<>();
        entries.add(new PeriodEntry("[25.05.2016-26.05.2016] Test text"));
        Compiler compiler = new Compiler();
        Stream<String> result = compiler.compile(entries);
        Optional<String> string = result.findFirst();
        string.ifPresent(entry -> assertEquals(
                "\\item[\\textbigcircle] [25.05.2016--26.05.2016] Test text",
                entry,
                "Wrong TeX generated"));
        if(!string.isPresent())
        {
            assertTrue(false, "No resulting string");
        }
    }

    /**
     * Tests NOTE entry generation.
     *
     * @throws ParseException Failed to parse test string.
     */
    @Test
    @DisplayName("Test NOTE entry generation.")
    public void testNoteEntryGeneration() throws ParseException
    {
        LinkedList<AbstractEntry> entries = new LinkedList<>();
        entries.add(new NoteEntry("~ Test"));
        Compiler compiler = new Compiler();
        Stream<String> result = compiler.compile(entries);
        Optional<String> string = result.findFirst();
        string.ifPresent(entry -> assertEquals("\\item[--] Test", entry,
                "Wrong TeX generated"));
        if(!string.isPresent())
        {
            assertTrue(false, "No resulting string");
        }
    }
}

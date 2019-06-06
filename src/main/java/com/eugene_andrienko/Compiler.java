package com.eugene_andrienko;

import com.eugene_andrienko.todo_entries.AbstractEntry;
import com.eugene_andrienko.todo_entries.DateTimeEntry;
import com.eugene_andrienko.todo_entries.NoteEntry;
import com.eugene_andrienko.todo_entries.PeriodEntry;
import com.eugene_andrienko.todo_entries.TodoEntry;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Translates TO-DO entries to TeX strings.
 */
public class Compiler
{
    /**
     * Generate set of TeX strings from given list of entries.
     *
     * @param entries List of entries.
     * @return Stream of TeX strings.
     */
    public Stream<String> compile(LinkedList<AbstractEntry> entries)
    {
        List<String> result = new LinkedList<>();
        for(AbstractEntry entry : entries)
        {
            try
            {
                result.add(generateTexString(entry));
            }
            catch(GenerateTeXException e)
            {
                System.err.println(
                        String.format(
                                "Failed to generate TeX string for entry: %s, with text: %s",
                                entry, entry.getText()));
            }
        }
        return result.stream();
    }

    /**
     * Generate TeX string from given entry.
     *
     * @param entry TO-DO entry.
     * @return Generated TeX string.
     * @throws GenerateTeXException Failed to generate TeX string.
     */
    private String generateTexString(AbstractEntry entry)
            throws GenerateTeXException
    {
        if(entry instanceof TodoEntry)
        {
            return generateTodoTexString((TodoEntry) entry);
        }
        else if(entry instanceof DateTimeEntry)
        {
            return generateDateTimeTexString((DateTimeEntry) entry);
        }
        else if(entry instanceof PeriodEntry)
        {
            return generatePeriodTexString((PeriodEntry) entry);
        }
        else if(entry instanceof NoteEntry)
        {
            return generateNoteTexString((NoteEntry) entry);
        }
        else
        {
            throw new GenerateTeXException();
        }
    }

    /**
     * Generate TeX string from TO-DO entry.
     *
     * @param entry TO-DO entry.
     * @return TeX string.
     */
    private String generateTodoTexString(TodoEntry entry)
    {
        return String.format("\\item %s", entry.getText());
    }

    /**
     * Generate TeX string for DATETIME entry.
     *
     * @param entry DATETIME entry.
     * @return TeX string.
     */
    private String generateDateTimeTexString(DateTimeEntry entry)
    {
        return String.format("\\item[\\textbigcircle] [%s] %s",
                entry.getDateTime().format(entry.getFormatter()),
                entry.getText());
    }

    /**
     * Generate TeX string for PERIOD entry.
     *
     * @param entry PERIOD entry.
     * @return Tex String.
     */
    private String generatePeriodTexString(PeriodEntry entry)
    {
        return String.format("\\item[\\textbigcircle] [%s--%s] %s",
                entry.getDateStart().format(entry.getFormatter()),
                entry.getDateEnd().format(entry.getFormatter()),
                entry.getText());
    }

    /**
     * Generate TeX string from NOTE entry.
     *
     * @param entry NOTE entry.
     * @return TeX string.
     */
    private String generateNoteTexString(NoteEntry entry)
    {
        return String.format("\\item[--] %s", entry.getText());
    }
}

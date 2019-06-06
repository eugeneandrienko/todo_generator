package com.eugene_andrienko;

import com.eugene_andrienko.todo_entries.AbstractEntry;
import com.eugene_andrienko.todo_entries.DateTimeEntry;
import com.eugene_andrienko.todo_entries.NoteEntry;
import com.eugene_andrienko.todo_entries.PeriodEntry;
import com.eugene_andrienko.todo_entries.TodoEntry;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.stream.Stream;

/**
 * Parses given TO-DO entries.
 */
public class Parser
{
    private enum EntryType
    {
        TODO, DATETIME, PERIOD, NOTE
    }

    public LinkedList<AbstractEntry> parse(Stream<String> entries)
    {
        final LinkedList<AbstractEntry> result = new LinkedList<>();
        entries.forEach(rawEntry ->
        {
            try
            {
                AbstractEntry entry = parseToDoEntry(rawEntry);
                result.add(entry);
            }
            catch(ParseException e)
            {
                System.err.println(
                        String.format(
                                "Failed to parse raw entry: %s", rawEntry));
            }
        });
        return result;
    }

    /**
     * Parses raw entry to some class, child of AbstractEntry.
     *
     * @param rawEntry Raw entry from text file.
     * @return Parsed entry.
     * @throws ParseException Failed to parse RAW entry.
     */
    private AbstractEntry parseToDoEntry(String rawEntry) throws ParseException
    {
        switch(getEntryType(rawEntry))
        {
            case TODO:
                return new TodoEntry(rawEntry);
            case DATETIME:
                return new DateTimeEntry(rawEntry);
            case PERIOD:
                return new PeriodEntry(rawEntry);
            case NOTE:
                return new NoteEntry(rawEntry);
            default:
                throw new ParseException("Unknown entry", 0);
        }
    }

    /**
     * Returns entry type.
     *
     * @param rawEntry Raw entry from text file.
     * @return Entry type.
     * @throws ParseException Failed to parse entry and get entry type.
     */
    private EntryType getEntryType(String rawEntry) throws ParseException
    {
        char firstCharacter = rawEntry.charAt(0);

        switch(firstCharacter)
        {
            case '-':
                return EntryType.TODO;
            case '[':
                if(rawEntry.contains("-"))
                {
                    return EntryType.PERIOD;
                }
                else
                {
                    return EntryType.DATETIME;
                }
            case '~':
                return EntryType.NOTE;
            default:
                throw new ParseException("Unknown entry type", 0);
        }
    }
}

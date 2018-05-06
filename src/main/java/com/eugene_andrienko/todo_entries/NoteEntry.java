package com.eugene_andrienko.todo_entries;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses notes from given TO-DO list.
 */
public class NoteEntry extends AbstractEntry
{
    public NoteEntry(String rawText) throws ParseException
    {
        super(rawText);
    }

    /**
     * Parses note item.
     *
     * @throws ParseException Parsing failed.
     */
    @Override
    protected void parse() throws ParseException
    {
        final String TODO_PATTERN = "^~\\s+(.*)";
        Pattern pattern = Pattern.compile(TODO_PATTERN);
        Matcher matcher = pattern.matcher(rawText);

        if(matcher.matches())
        {
            todoText = matcher.group(1);
        }
        else
        {
            throw new ParseException(rawText, 0);
        }
    }
}

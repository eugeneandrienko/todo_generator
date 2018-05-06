package com.eugene_andrienko.todo_entries;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses TO-DO items like: "-  text of entry".
 */
public class TodoEntry extends AbstractEntry
{
    public TodoEntry(String rawText) throws ParseException
    {
        super(rawText);
    }

    /**
     * Parses TO-DO item.
     *
     * @throws ParseException Parsing failed.
     */
    @Override
    protected void parse() throws ParseException
    {
        final String TODO_PATTERN = "^-\\s+(.*)";
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

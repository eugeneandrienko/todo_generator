package com.eugene_andrienko.todo_entries;

import java.text.ParseException;

/**
 * Base entry class for TO-DO list(s).
 */
public abstract class AbstractEntry
{
    protected String rawText;
    protected String todoText;

    /**
     * Creates entry object and parses incoming data.
     *
     * @param rawText Incoming text.
     * @throws ParseException Parsing failed.
     */
    public AbstractEntry(final String rawText) throws ParseException
    {
        this.rawText = rawText;
        parse();
    }

    /**
     * Parses incoming text.
     *
     * @throws ParseException Parsing failed.
     */
    protected abstract void parse() throws ParseException;

    /**
     * Returns parsed text of TO-DO item.
     *
     * @return Parsed text.
     */
    public String getText()
    {
        return todoText;
    }
}

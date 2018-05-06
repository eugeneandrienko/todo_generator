package com.eugene_andrienko.todo_entries;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses DATE entries like: "[datetime] text of entry".
 * <p>
 * List of valuable entries:
 * Simple date: "[20.05.2017] Text"
 * Simple date and time: "[20.05.2017 20:00] Text"
 */
public class DateTimeEntry extends AbstractEntry
{
    private LocalDateTime dateTime;
    private DateTimeFormatter formatter;

    public DateTimeEntry(String rawText) throws ParseException
    {
        super(rawText);
    }

    /**
     * Returns date given in input string.
     *
     * @return Date for entry.
     */
    public LocalDateTime getDateTime()
    {
        return dateTime;
    }

    /**
     * Returns proper formatter for date or datetime.
     *
     * @return Date-time formatter.
     */
    public DateTimeFormatter getFormatter()
    {
        return formatter;
    }

    /**
     * Parses DATE entry.
     *
     * @throws ParseException Parsing failed.
     */
    @Override
    protected void parse() throws ParseException
    {
        final String DATETIME_PATTERN =
                "^\\[(\\d{2}\\.\\d{2}\\.\\d{4}( \\d{2}:\\d{2})?)]\\s+(.*)";
        Pattern pattern = Pattern.compile(DATETIME_PATTERN);
        Matcher matcher = pattern.matcher(rawText);

        if(matcher.matches())
        {
            todoText = matcher.group(3);
            parseDate(matcher.group(1));
        }
        else
        {
            throw new ParseException(rawText, 0);
        }
    }

    /**
     * Parses datetime entered by user in entry.
     *
     * @param datetimeString Datetime string from entry.
     */
    private void parseDate(String datetimeString)
    {
        final String DATETIME_PATTERN =
                "^\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}:\\d{2}";
        Pattern pattern = Pattern.compile(DATETIME_PATTERN);
        Matcher matcher = pattern.matcher(datetimeString);

        if(matcher.matches())
        {
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .parseStrict()
                    .appendPattern("dd.MM.uuuu HH:mm")
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);
            dateTime = LocalDateTime.parse(datetimeString, formatter);
            this.formatter = formatter;
        }
        else
        {
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .parseStrict()
                    .appendPattern("dd.MM.uuuu")
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);
            dateTime = LocalDateTime.of(
                    LocalDate.parse(datetimeString, formatter),
                    LocalTime.MIDNIGHT);
            this.formatter = formatter;
        }
    }
}

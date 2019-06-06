package com.eugene_andrienko.todo_entries;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Parses period entries like: "[date] text of entry".
 * <p>
 * List of valuable entries:
 * Date period: "[01.02.2000-03.04.2005] Text"
 */
public class PeriodEntry extends AbstractEntry
{
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private DateTimeFormatter formatter;

    public PeriodEntry(String rawText) throws ParseException
    {
        super(rawText);
    }

    /**
     * Returns start date given in input string.
     * @return Start date for entry.
     */
    public LocalDate getDateStart()
    {
        return dateStart;
    }

    /**
     * Returns end date given in input string.
     * @return End date for entry.
     */
    public LocalDate getDateEnd()
    {
        return dateEnd;
    }

    /**
     * Returns proper formatter for date.
     *
     * @return Date formatter.
     */
    public DateTimeFormatter getFormatter()
    {
        return formatter;
    }

    /**
     * Parses PERIOD entry.
     *
     * @throws ParseException Parsing failed.
     */
    @Override
    protected void parse() throws ParseException
    {
        final String PERIOD_PATTERN =
                "^\\[" +
                "(\\d{2}\\.\\d{2}\\.\\d{4})" +
                "-" +
                "(\\d{2}\\.\\d{2}\\.\\d{4})" +
                "]\\s+(.*)";
        Pattern pattern = Pattern.compile(PERIOD_PATTERN);
        Matcher matcher = pattern.matcher(rawText);

        if(matcher.matches())
        {
            todoText = matcher.group(3);
            parsePeriod(matcher.group(1), matcher.group(2));
        }
        else
        {
            throw new ParseException(rawText, 0);
        }
    }

    /**
     * Parses dates for period.
     *
     * @param dateStartString Start date.
     * @param dateEndString   End date.
     *
     * @throws ParseException Failed to parsing period.
     */
    private void parsePeriod(String dateStartString, String dateEndString)
            throws ParseException
    {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseStrict()
                .appendPattern("dd.MM.uuuu")
                .toFormatter()
                .withResolverStyle(ResolverStyle.STRICT);
        dateStart = LocalDate.parse(dateStartString, formatter);
        dateEnd = LocalDate.parse(dateEndString, formatter);
        if(dateStart.isAfter(dateEnd) || dateStart.isEqual(dateEnd))
        {
            throw new ParseException(rawText, 1);
        }
        this.formatter = formatter;
    }
}

package com.eugene_andrienko;

import com.eugene_andrienko.todo_entries.AbstractEntry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.stream.Stream;

public class Main
{
    /**
     * Prints help message to user.
     */
    private static void printHelpMessage()
    {
        String message = "program.jar INPUTF OUTPUTF\n" +
                "\t INPUTF - path to file with TODO entries\n" +
                "\t OUTPUTF - path to generated file with TeX commands";
        System.err.println(message);
    }

    public static void main(String[] args) throws IOException
    {
        if(args.length != 2)
        {
            printHelpMessage();
            return;
        }

        // Read strings from STDIN:
        String inputFilename = args[0];
        Stream<String> inputStream = Files.lines(Paths.get(inputFilename))
                                          .filter(line -> line.length() != 0);

        // Parsing data:
        Parser parser = new Parser();
        LinkedList<AbstractEntry> parsedEntries = parser.parse(inputStream);

        // Write strings to STDOUT:
        Compiler compiler = new Compiler();
        Stream<String> outputStream = compiler.compile(parsedEntries);
        String outputFilename = args[1];
        Files.write(Paths.get(outputFilename),
                (Iterable<String>) outputStream::iterator);
    }
}

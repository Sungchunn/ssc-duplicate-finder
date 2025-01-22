package io.muzoo.ssc.cli;

import org.apache.commons.cli.ParseException;

public interface ICommandLineParser {
    CommandLineConfig parse(String[] args) throws ParseException;
}
package com.example.todo;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CommandLine {

    private static final int MIN_COMMAND_LENGTH = 2;
    private String[] commands;
    private final String command;

    public CommandLine(final String commands) {
        this.command = commands.trim();
        this.commands = StringUtils.splitByWholeSeparator(command, " ");
        verify();
    }

    private void  verify() {
        if (StringUtils.isBlank(command)) {
            throw new IllegalArgumentException("command line illegal");
        }
        if (!"todo".equalsIgnoreCase(commands[0])) {
            throw new IllegalArgumentException("command line must start with todo");
        }
        if (commands.length < MIN_COMMAND_LENGTH) {
            throw new IllegalArgumentException("command line illegal, ensure you command length not less than 2");

        }
    }

    public String operation() {
        return commands[1];
    }

    public String todoContent() {
        return IntStream.range(0, commands.length)
                .filter(index -> index >= 2)
                .mapToObj(index -> commands[index] + " ")
                .collect(Collectors.joining()).trim();

    }


}

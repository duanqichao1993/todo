package com.example.todo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * command line valid test
 */
public class CommandLineTest {

    @Test
    @DisplayName("should parse add command")
    public void should_parse_add_command() {
        String commandLine = "todo add  toSwim ";
        CommandLine userInput = new CommandLine(commandLine);
        Assertions.assertEquals("add",userInput.operation());
        Assertions.assertEquals("toSwim",userInput.todoContent());
    }








}

package com.example.todo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

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
    @Test
    @DisplayName("should throw illegaArgumentEception if command is not correct")
    public void should_throw_IllegalArgumentException_if_command_is_not_correct(){
        String emptyCommand = " ";
        Assertions.assertThrows(IllegalArgumentException.class, () ->{new CommandLine(emptyCommand);}, "command line illegal");
        String errorCommand = "xxx add to swim";
        Assertions.assertThrows(IllegalArgumentException.class, () ->{new CommandLine(errorCommand);}, "command line must start with todo");
        String shortCommand = "todo  ";
        Assertions.assertThrows(IllegalArgumentException.class, () ->{new CommandLine(shortCommand);}, "command line illegal, ensure you command length not less than 2");
    }






}

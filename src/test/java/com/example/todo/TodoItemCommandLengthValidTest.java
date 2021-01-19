package com.example.todo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * command line valid test
 */
public class TodoItemCommandLengthValidTest {

    @Test
    @DisplayName("should receive user input")
    public void should_receive_user_input() {
        String commandLine = "todo add  toSwim ";
        CommandLine userInput = new CommandLine(commandLine);
        boolean valid = userInput.verify();
        Assertions.assertTrue(valid);
    }





}

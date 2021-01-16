package com.example.todo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

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

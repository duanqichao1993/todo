package com.example.todo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TodoItemCommandParseTest {

    @Test
    @DisplayName("should parse command is add")
    public void should_parse_command_is_add() {
        String commandLine = "todo add xxx";
        CommandLine userInput = buildCommandLine(commandLine);
        String operation = userInput.operation();
        Assertions.assertEquals(operation, "add");
    }


    @Test
    @DisplayName("should parse command is delete")
    public void should_parse_command_is_delete() {
        String deleteCommand = "todo delete xxx";
        CommandLine commandLine = buildCommandLine(deleteCommand);
        String delOperation = commandLine.operation();
        Assertions.assertEquals(delOperation, "delete");
    }

    @Test
    @DisplayName("should parse todo date ")
    public void should_parse_todo_date() {
        String addCommand = "todo add go home";
        CommandLine commandLine = buildCommandLine(addCommand);
        String todoContent = commandLine.todoContent();
        Assertions.assertEquals("go home", todoContent);
    }

    private CommandLine buildCommandLine(String addCommand) {
        return new CommandLine(addCommand);
    }

    @Test
    @DisplayName("should get todo from commandLine")
    public void should_get_todo_from_command_line() {
        CommandLine commandLine = new CommandLine("todo add go home");
        TodoItem todoItem = new TodoItem(commandLine.operation(), commandLine.todoContent(), 1);
        Assertions.assertNotNull(todoItem);
        Assertions.assertEquals("add", todoItem.operation());
        Assertions.assertEquals("go home", todoItem.userDate());
    }


}

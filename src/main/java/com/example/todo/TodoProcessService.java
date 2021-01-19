package com.example.todo;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TodoProcessService {


    private final TodoFile todoFile;

    public TodoProcessService(TodoFile todoFile) {

        this.todoFile = todoFile;
    }

    public String save(TodoItem todoItem) throws IOException {
        todoFile.addItemToFile(todoItem);
        return this.report(todoItem, todoItem.operation());
    }

    private String report(TodoItem todoItem, String operation) {
        String action = "";
        if ("add".equals(operation)) {
            action = "added";
        }
        if ("done".equals(operation)) {
            action = "done";
        }
        return "Item <" + todoItem.index() + "> " + action;
    }

    public String doneTask(int taskIndex) {
        try {
            List<TodoItem> todoItems = todoFile.readTodoItems();
            for (TodoItem todoItem : todoItems) {
                int index = todoItem.index();
                if (index == taskIndex) {
                    todoFile.doneTask(taskIndex);
                    return this.report(todoItem, "done");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String listAllTaskNotDone() throws IOException {
        List<TodoItem> todoItems = todoFile.readTodoItems();
        List<TodoItem> collect = todoItems.stream().filter(todoItem -> !todoItem.doneState()).collect(Collectors.toList());
        return this.formatDoingTask(collect);
    }


    private String formatDoingTask(List<TodoItem> collect) {
        StringBuilder messageBuilder = new StringBuilder();
        collect.forEach(todoItem -> messageBuilder.append(todoItem.index()).append(".").append(" ").append(todoItem.userDate()).append(System.lineSeparator()));
        messageBuilder.append(System.lineSeparator()).append("Total:").append(collect.size()).append("  ").append("items");
        return messageBuilder.toString();

    }

    public String formatAllTaskInfo(List<TodoItem> collect) {
        StringBuilder taskInfoBuilder = new StringBuilder();
        int doneNum = 0;
        for (TodoItem todoItem : collect) {
            if (!todoItem.doneState()) {
                taskInfoBuilder.append(todoItem.index()).append(".").append(todoItem.userDate()).append(System.lineSeparator());
            } else {
                taskInfoBuilder.append(todoItem.index()).append(".").append("[Done]").append(todoItem.userDate()).append(System.lineSeparator());
                doneNum++;
            }
        }
        taskInfoBuilder.append("Total: ").append(collect.size()).append(" ,").append(doneNum).append(" item done");
        return taskInfoBuilder.toString();
    }

    public String listAllTask() throws IOException {
        List<TodoItem> todoItems = todoFile.readTodoItems();
        return this.formatAllTaskInfo(todoItems);

    }

    public String process(String userInput) throws IOException {
        CommandLine commandLine = new CommandLine(userInput);
        String operation = commandLine.operation();
        if ("add".equals(operation)) {
            return this.save(new TodoItem(commandLine.operation(), commandLine.todoContent(), todoFile.getLastIndex()));
        }
        if ("done".equals(operation)) {
            return this.doneTask(Integer.parseInt(commandLine.todoContent()));
        }
        if ("list".equals(operation)) {
            if (StringUtils.isNotBlank(commandLine.todoContent())) {
                return this.listAllTask();
            }
            return this.listAllTaskNotDone();
        }
        if ("exit".equals(operation)) {
            System.exit(0);
        }
        return "command not execute";
    }
}

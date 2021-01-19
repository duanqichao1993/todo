package com.example.todo;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TodoProcessService {


    private final FileOperation fileOperation;

    public TodoProcessService(FileOperation fileOperation) {

        this.fileOperation = fileOperation;
    }

    public String save(TodoItem todoItem) throws IOException {
        fileOperation.addItemToFile(todoItem);
        return this.report(todoItem, todoItem.operation());
    }

    public String report(TodoItem todoItem, String operation) {
        String action = "";
        if ("add".equals(operation)) {
            action = "added";
        }
        if ("done".equals(operation)) {
            action = "done";
        }
        return "Item <" + todoItem.index() + "> " + action;
    }

    public String doneTask(int i) {
        try {
            String fileContent = fileOperation.readFromFile();
            List<TodoItem> todoItems = fileOperation.parseTodoFileTodoItem(fileContent);
            for (TodoItem todoItem : todoItems) {
                int index = todoItem.index();
                if (index == i) {
                    todoItem.done();
                    return this.report(todoItem, "done");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String listAllTaskNotDone() throws IOException {
        String message = fileOperation.readFromFile();
        List<TodoItem> todoItems = fileOperation.parseTodoFileTodoItem(message);
        List<TodoItem> collect = todoItems.stream().filter(todoItem -> !todoItem.doneState()).collect(Collectors.toList());
        return this.formatDoingTask(collect);
    }

    private String formatDoingTask(List<TodoItem> collect) {
        StringBuilder messageBuilder = new StringBuilder();
        collect.forEach(todoItem -> messageBuilder.append(todoItem.index()).append(".").append(" ").append(todoItem.userDate()).append(System.lineSeparator())
                .append(System.lineSeparator()).append("Total:").append(collect.size()).append("  ").append("items"));
        System.out.println(messageBuilder.toString());
        return messageBuilder.toString();

    }

    public String formatAllTaskInfo(List<TodoItem> collect) {
        StringBuilder  taskInfoBuilder =  new StringBuilder();
        int doneNum = 0;
        for (TodoItem todoItem : collect) {
            if (!todoItem.doneState()) {
                taskInfoBuilder.append(todoItem.index()).append(".").append(todoItem.userDate()).append(System.lineSeparator());
            }else{
                taskInfoBuilder.append(todoItem.index()).append(".").append("[Done]").append(todoItem.userDate()).append(System.lineSeparator());
                doneNum++;
            }
        }
        taskInfoBuilder.append("Total: ").append(collect.size()).append(" ,").append(doneNum).append(" item done");
        System.out.println(taskInfoBuilder.toString());
        return taskInfoBuilder.toString();
    }

    public String listAllTask() throws IOException {
        String message = fileOperation.readFromFile();
        List<TodoItem> todoItems = fileOperation.parseTodoFileTodoItem(message);
        return this.formatAllTaskInfo(todoItems);

    }
}

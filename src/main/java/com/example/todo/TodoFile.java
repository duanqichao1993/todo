package com.example.todo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.io.CharSink;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
@Log
public class TodoFile {

    private final File file;
    private final static int FIRST_INDEX = 1;
    private final  ObjectMapper objectMapper = new ObjectMapper();

    public TodoFile(String path) {
        file = new File(path);
        if (!file.exists()) {
            try {
                Files.touch(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void addItemToFile(TodoItem todoItem) throws IOException {
        String existFileContent = this.readFromFile();
        if (StringUtils.isBlank(existFileContent)) {
            writeFile(ImmutableList.of(todoItem));
            return;
        }
        List<TodoItem> todoItems = addNewItem(todoItem, existFileContent);
        writeFile(todoItems);
    }



    private List<TodoItem> addNewItem(TodoItem todoItem, String existFileContent) throws JsonProcessingException {
        List<TodoItem> todoItems = parseTodoFileTodoItem(existFileContent);
        todoItems.add(todoItem);
        return todoItems;
    }

    private void writeFile(List<TodoItem> todoItems) throws IOException {
        CharSink charSink = Files.asCharSink(file, StandardCharsets.UTF_8);
        String lastFileContent = objectMapper.writeValueAsString(todoItems);
        charSink.writeLines(ImmutableList.of(lastFileContent));
    }

    public int getLastIndex() throws IOException {
        String content = this.readFromFile();
        if (StringUtils.isBlank(content)) {
            return FIRST_INDEX;
        }
        List<TodoItem> todoItems = parseTodoFileTodoItem(content);
        return nextItemIndex(todoItems);

    }

    private int nextItemIndex(List<TodoItem> todoItems) {
        return todoItems.get(todoItems.size() - 1).index() + 1;
    }

    private List<TodoItem> parseTodoFileTodoItem(String content)
            throws JsonProcessingException {
        if (StringUtils.isBlank(content)) {
            return Collections.emptyList();
        }
        return objectMapper.readValue(content, new TypeReference<List<TodoItem>>() {
        });
    }

    public String readFromFile() throws IOException {
        CharSource charSource = Files.asCharSource(file, StandardCharsets.UTF_8);
        return charSource.read();
    }


    public void delete() {
        if (file.exists()) {
            file.delete();
        }
    }

    public int todoSize() {
        try {
            return parseTodoFileTodoItem(this.readFromFile()).size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  0 ;
    }

    public void doneTask(int taskIndex) throws IOException {
        String existContent = this.readFromFile();
        List<TodoItem> todoItems = this.parseTodoFileTodoItem(existContent);
        todoItems.forEach(todoItem -> {
            int index = todoItem.index();
            if (index == taskIndex) {
                todoItem.done();
            }
        });
        this.writeFile(todoItems);
    }

    public List<TodoItem> readTodoItems() throws IOException {
        String fileContent = this.readFromFile();
        return this.parseTodoFileTodoItem(fileContent);
    }
}

package com.example.todo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.io.CharSink;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileRecord {

    private final File file;
    private final List<TodoItem> todoItems = new ArrayList<>();
    private final static int FIRST_INDEX = 1;

    public FileRecord(String path) {
        file = new File(path);
        if (!file.exists()) {
            try {
                Files.touch(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String writeToFile(TodoItem todoItem) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        todoItems.add(todoItem);
        CharSink charSink = getCharSink();
        String todoString = objectMapper.writeValueAsString(todoItems);
        charSink.writeLines(ImmutableList.of(todoString));
        return this.readFromFile();
    }

    public int getLastIndex() throws IOException {
        String content = this.readFromFile();
        if (StringUtils.isBlank(content)) {
            return FIRST_INDEX;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<TodoItem> todoItems = parseTodoFileTodoItem(content, objectMapper);
        return nextItemIndex(todoItems);

    }

    private int nextItemIndex(List<TodoItem> todoItems) {
        return todoItems.get(todoItems.size() - 1).index() + 1;
    }

    private List<TodoItem> parseTodoFileTodoItem(String content, ObjectMapper objectMapper)
            throws com.fasterxml.jackson.core.JsonProcessingException {
        return objectMapper.readValue(content, new TypeReference<List<TodoItem>>() {
        });
    }

    private CharSink getCharSink() {
        return Files.asCharSink(file, StandardCharsets.UTF_8);
    }

    public String readFromFile() throws IOException {
        CharSource charSource = Files.asCharSource(file, StandardCharsets.UTF_8);
        return charSource.read();
    }


    public void delete() {
        file.delete();
    }

}

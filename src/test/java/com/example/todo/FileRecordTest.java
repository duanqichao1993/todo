package com.example.todo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;

import java.io.IOException;

public class FileRecordTest {

    private FileRecord fileRecord;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        fileRecord = new FileRecord();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("should complete todo item index")
    public void should_complete_todo_item_index() throws IOException {
        CommandLine commandLine = new CommandLine("todo add go home");
        int index = fileRecord.getLastIndex();
        TodoItem todoItem = new TodoItem(commandLine.operation(), commandLine.todoContent(),index);
        fileRecord.writeToFile(todoItem);

    }

    @Test
    @DisplayName("should write todo content to file")
    public void should_write_todo_content_to_file() throws IOException {
        String firstWrite = fileRecord.writeToFile(new TodoItem("add", "go home", 1));
        String sendWrite = fileRecord.writeToFile(new TodoItem("add", "to swim", 2));

        JsonNode firstWriteJson = convertJSON(firstWrite);
        Assertions.assertEquals(1, firstWriteJson.size());

        JsonNode secondWriteJson = convertJSON(sendWrite);
        Assertions.assertEquals(secondWriteJson.size(), 2);

        fileRecord.delete();
    }

    private JsonNode convertJSON(String contentJson) throws JsonProcessingException {
        return objectMapper.readTree(contentJson);
    }




}

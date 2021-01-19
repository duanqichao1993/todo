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
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("should get index is 1 if file is black or not exist")
    public void should_complete_todo_item_index() throws IOException {
        fileRecord = new FileRecord("src/test/resources/todoEmpty.txt");
        int index = fileRecord.getLastIndex();
        Assertions.assertEquals(1, index);
    }

    @Test
    @DisplayName("should get index is 2 if todo file is not empty")
    public void should_get_index_is_2_if_todo_file_is_not_empty() throws IOException {
        fileRecord = new FileRecord("src/test/resources/todoNotEmpty.txt");
        int index = fileRecord.getLastIndex();
        Assertions.assertEquals(2, index);
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

package com.example.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class TodoFileTest {

    private TodoFile todoFile;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("should get index is 1 if file is blank or not exist")
    public void should_get_index_is_1_if_file_is_blank_or_not_exist() throws IOException {
        todoFile = new TodoFile("src/test/resources/todoEmpty.txt");
        int index = todoFile.getLastIndex();
        Assertions.assertEquals(1, index);
    }

    @Test
    @DisplayName("should get index is 2 if todo file is not empty")
    public void should_get_index_is_2_if_todo_file_is_not_empty() throws IOException {
        todoFile = new TodoFile("src/test/resources/todoNotEmpty.txt");
        int index = todoFile.getLastIndex();
        Assertions.assertEquals(2, index);
    }


    @Test
    @DisplayName("should add todo content to file")
    public void should_add_todo_content_to_file() throws IOException {
        todoFile = new TodoFile("todo.txt");
        todoFile.addItemToFile(new TodoItem("add", "go home", 1));
        Assertions.assertEquals(1, todoFile.todoSize());

        todoFile.addItemToFile(new TodoItem("add", "to swim", 2));
        Assertions.assertEquals(2, todoFile.todoSize());
        todoFile.delete();
    }

    @Test
    @DisplayName("should_done_task")
    public void should_done_task() throws IOException {
        todoFile = new TodoFile("src/test/resources/todoNotDone.txt");
        int taskIndex = 1;
        todoFile.doneTask(taskIndex);
        List<TodoItem> todoItems = todoFile.readTodoItems();
        Optional<TodoItem> first = todoItems.stream().filter(todoItem -> todoItem.index() == taskIndex).findFirst();
        first.ifPresent(todoItem -> Assertions.assertTrue(todoItem.doneState()));
    }



}

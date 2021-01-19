package com.example.todo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class TodoProcessServiceTest {

    private FileOperation fileOperation;
    private TodoProcessService todoProcessService;

    @BeforeEach
    public void setUp() {
        this.fileOperation = new FileOperation("todo.txt");
    }

    @Test
    @DisplayName("should save todo to file if command is add")
    public void should_save_todo_to_file_if_command_is_add() throws IOException {
        int lastIndex = fileOperation.getLastIndex();
        this.todoProcessService = new TodoProcessService(fileOperation);
        String reportMessage = todoProcessService.save(new TodoItem("add", "go home", lastIndex));
        Assertions.assertEquals(1, fileOperation.todoSize());
        Assertions.assertEquals("Item <" + lastIndex + "> added", reportMessage);
        fileOperation.delete();
    }

    @Test
    @DisplayName("should done todo task if command is done")
    public void should_done_todo_task_if_command_is_done() {
        fileOperation = new FileOperation("src/test/resources/todoNotDone.txt");
        this.todoProcessService = new TodoProcessService(fileOperation);
        int index = 1;
        String doneMessage = todoProcessService.doneTask(index);
        Assertions.assertEquals("Item <" + index + "> done", doneMessage);
    }

    @Test
    @DisplayName("should show task that not done ")
    public void should_show_task_that_not_done() throws IOException {
        initTestFile();
        String message =  todoProcessService.listAllTaskNotDone();
        String exceptMessage= "2. to swim" + System.lineSeparator()  + System.lineSeparator()+ "Total:1  items";
        Assertions.assertEquals(exceptMessage, message);
    }

    @Test
    @DisplayName("should show all task")
    public void should_show_all_task() throws IOException {
        initTestFile();
        String taskInfo = todoProcessService.listAllTask();
        String message = "1.[Done]go home\n" +
                "2.to swim\n" +
                "Total: 2 ,1 item done";
        Assertions.assertEquals(message, taskInfo);

    }

    private void initTestFile() {
        FileOperation fileOperation = new FileOperation("src/test/resources/todoListTest.txt");
        this.todoProcessService = new TodoProcessService(fileOperation);
    }

}
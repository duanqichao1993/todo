package com.example.todo;

import java.io.IOException;
import java.util.Scanner;

public class TodoProgram {
    public static void main(String[] args)   {
        TodoFile todoFile = new TodoFile("todo.txt");
        TodoProcessService todoProcessService = new TodoProcessService(todoFile);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("please input todo command line , if you went exit ,input 'todo exit'");
            String userInput = scanner.nextLine();
            String message = null;
            try {
                message = todoProcessService.process(userInput);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(message);
        }
    }

}

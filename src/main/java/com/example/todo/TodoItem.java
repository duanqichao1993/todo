package com.example.todo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

public class TodoItem {
    @JsonProperty
    private final String operation;
    @JsonProperty
    private final String todoData;

    @JsonProperty
    private boolean doneState;
    @JsonProperty
    private int index ;

    @JsonCreator
    public TodoItem(@JsonProperty("operation") String operation, @JsonProperty("todoData") String todoData, @JsonProperty("index") int index) {
        this.operation = operation;
        this.todoData = todoData;
        this.doneState = false;
        this.index = index;
    }


    public String operation() {
        return this.operation;
    }

    public String userDate() {
        return this.todoData;
    }

    public void done() {
        this.doneState = true;
    }

    public int  index() {
        return this.index;
    }




}

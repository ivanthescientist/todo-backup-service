package com.isc.todo.backup.configuration;

import com.isc.todo.backup.contract.TodoDTO;
import com.isc.todo.backup.service.TodoService;

import java.util.List;
import java.util.Optional;

public class TestTodoService implements TodoService {
    private List<TodoDTO> data;

    public void setData(List<TodoDTO> data) {
        this.data = data;
    }

    @Override
    public Optional<List<TodoDTO>> findAll() {
        return Optional.ofNullable(data);
    }
}

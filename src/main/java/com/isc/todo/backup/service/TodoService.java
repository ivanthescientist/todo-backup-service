package com.isc.todo.backup.service;

import com.isc.todo.backup.contract.TodoDTO;

import java.util.List;
import java.util.Optional;

public interface TodoService {
    Optional<List<TodoDTO>> findAll();
}

package com.isc.todo.backup.service;

import com.isc.todo.backup.contract.BackupStatus;
import com.isc.todo.backup.contract.TodoBackupDTO;
import com.isc.todo.backup.contract.TodoDTO;

import java.util.List;
import java.util.Optional;

public class BackupTask implements Runnable {
    private final TodoBackupDTO todoBackupDTO;
    private final TodoService todoService;

    public BackupTask(TodoBackupDTO todoBackupDTO, TodoService todoService) {
        this.todoBackupDTO = todoBackupDTO;
        this.todoService = todoService;
    }

    @Override
    public void run() {
        Optional<List<TodoDTO>> result = todoService.findAll();

        if(result.isPresent()) {
            todoBackupDTO.setPayload(result.get());
            todoBackupDTO.setStatus(BackupStatus.OK);
        } else {
            todoBackupDTO.setStatus(BackupStatus.FAILED);
        }
    }
}

package com.isc.todo.backup.service;

import com.isc.todo.backup.contract.BackupStatus;
import com.isc.todo.backup.contract.TodoBackupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BackupService {
    private final AtomicLong counter = new AtomicLong(0L);
    private final TaskExecutor executor;
    private final TodoService todoService;
    private final Map<Long, TodoBackupDTO> storage = new ConcurrentHashMap<>();

    @Autowired
    public BackupService(TaskExecutor executor, TodoService todoService) {
        this.executor = executor;
        this.todoService = todoService;
    }

    public TodoBackupDTO create() {
        TodoBackupDTO backup = new TodoBackupDTO();
        backup.setBackupId(counter.incrementAndGet());
        backup.setDate(new Date());
        backup.setStatus(BackupStatus.IN_PROGRESS);
        backup.setPayload(new ArrayList<>());

        storage.put(backup.getBackupId(), backup);

        executor.execute(new BackupTask(backup, todoService));

        return backup;
    }

    public Collection<TodoBackupDTO> findAll() {
        return storage.values();
    }

    public Optional<TodoBackupDTO> findOne(Long id) {
        return Optional.ofNullable(storage.get(id));
    }
}

package com.isc.todo.backup.controller;

import com.isc.todo.backup.contract.TodoBackupDTO;
import com.isc.todo.backup.contract.TodoDTO;
import com.isc.todo.backup.exception.BackupInProgressException;
import com.isc.todo.backup.exception.BackupNotFoundException;
import com.isc.todo.backup.exception.NoBackupDataFoundException;
import com.isc.todo.backup.service.BackupDataSerializer;
import com.isc.todo.backup.service.BackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/backups")
public class TodoBackupController {
    private final BackupService backupService;
    private final BackupDataSerializer dataSerializer;

    @Autowired
    public TodoBackupController(BackupService backupService, BackupDataSerializer dataSerializer) {
        this.backupService = backupService;
        this.dataSerializer = dataSerializer;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public TodoBackupDTO create() {
        return backupService.create();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Collection<TodoBackupDTO> findAll() {
        return backupService.findAll();
    }

    @RequestMapping(value = "/{backupId}", method = RequestMethod.GET)
    public void getById(@PathVariable Long backupId, HttpServletResponse response) throws IOException {
        TodoBackupDTO backup = backupService.findOne(backupId)
                .orElseThrow(() -> new BackupNotFoundException(backupId));

        switch (backup.getStatus()) {
            case FAILED:
                throw new BackupInProgressException(backupId);
            case IN_PROGRESS:
                throw new NoBackupDataFoundException(backupId);
        }

        String body = dataSerializer.serialize(backup.getPayload());
        String fileName = "todo-backup-" + backupId;

        response.setHeader("Content-Type", "application/csv;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(body);
        response.getWriter().flush();
        response.getWriter().close();
    }

    @ExceptionHandler(BackupInProgressException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void backupInProgressExceptionHandler() {}

    @ExceptionHandler(BackupNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void backupNotFoundException() {}

    @ExceptionHandler(NoBackupDataFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void noBackupDataFoundException() {}
}

package com.isc.todo.backup.exception;

public class NoBackupDataFoundException extends RuntimeException {
    public NoBackupDataFoundException(Long backupId) {
        super("No data found in backup #" + backupId);
    }
}

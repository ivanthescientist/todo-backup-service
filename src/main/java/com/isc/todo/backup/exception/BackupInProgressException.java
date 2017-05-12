package com.isc.todo.backup.exception;

public class BackupInProgressException extends RuntimeException {
    public BackupInProgressException(Long backupId) {
        super("Backup #" + backupId + " is still in progress");
    }
}

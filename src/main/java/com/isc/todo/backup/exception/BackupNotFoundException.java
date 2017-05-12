package com.isc.todo.backup.exception;

public class BackupNotFoundException extends RuntimeException {
    public BackupNotFoundException(Long backupId) {
        super("Backup #" + backupId.toString() + " not found.");
    }
}

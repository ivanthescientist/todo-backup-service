package com.isc.todo.backup.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

public class TodoBackupDTO {
    private Long backupId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    private BackupStatus status;

    @JsonIgnore
    private List<TodoDTO> payload;

    public Long getBackupId() {
        return backupId;
    }

    public void setBackupId(Long backupId) {
        this.backupId = backupId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BackupStatus getStatus() {
        return status;
    }

    public void setStatus(BackupStatus status) {
        this.status = status;
    }

    public List<TodoDTO> getPayload() {
        return payload;
    }

    public void setPayload(List<TodoDTO> payload) {
        this.payload = payload;
    }
}

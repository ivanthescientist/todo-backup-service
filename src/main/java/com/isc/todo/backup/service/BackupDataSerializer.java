package com.isc.todo.backup.service;

import com.isc.todo.backup.contract.TodoDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

@Service
public class BackupDataSerializer {
    private final CSVFormat format;
    private final DateFormat dateFormat;

    @Autowired
    public BackupDataSerializer(CSVFormat format, DateFormat dateFormat) {
        this.format = format;
        this.dateFormat = dateFormat;
    }

    public String serialize(List<TodoDTO> data) throws IOException {
        StringBuilder appendable = new StringBuilder();
        CSVPrinter csvPrinter = new CSVPrinter(appendable, format);
        csvPrinter.printRecord("Username", "Subject", "DueDate", "Done");

        for(TodoDTO todoDTO : data) {
            csvPrinter.printRecord(todoDTO.getUsername(), todoDTO.getSubject(), printDate(todoDTO.getDueDate()), todoDTO.getDone());
        }

        csvPrinter.flush();

        return appendable.toString();
    }

    private String printDate(Date date) {
        return dateFormat.format(date);
    }
}

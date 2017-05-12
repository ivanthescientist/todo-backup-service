package com.isc.todo.backup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(TodoBackupConfiguration.class)
public class TodoBackupApplication {
    public static void main(String[] args) {
        SpringApplication.run(TodoBackupApplication.class, args);
    }
}

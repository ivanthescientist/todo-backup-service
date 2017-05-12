package com.isc.todo.backup.configuration;

import com.isc.todo.backup.contract.TodoDTO;
import com.isc.todo.backup.service.TodoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import java.util.Collections;
import java.util.Date;

@Configuration
public class TestConfiguration {

    @Bean
    @Primary
    public TodoService todoService() {
        TestTodoService testTodoService = new TestTodoService();

        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setUsername("user1");
        todoDTO.setDueDate(new Date());
        todoDTO.setSubject("todo1");
        todoDTO.setDone(false);

        testTodoService.setData(Collections.singletonList(todoDTO));

        return testTodoService;
    }

    @Bean
    @Primary
    public TaskExecutor testTaskExecutor() {
        return new SyncTaskExecutor();
    }
}

package com.isc.todo.backup.service;

import com.isc.todo.backup.contract.TodoDTO;
import com.isc.todo.backup.contract.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ExternalTodoService implements TodoService {
    private final Logger logger = LoggerFactory.getLogger(ExternalTodoService.class);
    private final String serverAddress;
    private final RestTemplate restTemplate = new RestTemplate();

    public ExternalTodoService(@Value("${todo.server.address}") String serverAddress) {
        this.serverAddress = serverAddress;
    }

    @Override
    public Optional<List<TodoDTO>> findAll() {
        try {
            return Optional.of(getUsers()
                    .flatMap(this::getUserTodos)
                    .collect(Collectors.toList()));
        } catch (RestClientException exception) {
            logger.debug("Failed to fetch todos: ", exception);
            return Optional.empty();
        }
    }

    private Stream<UserDTO> getUsers() {
        return Arrays.stream(restTemplate.getForObject(serverAddress + "/users", UserDTO[].class));
    }

    private Stream<TodoDTO> getUserTodos(UserDTO userDTO) {
        return userDTO.getTodos().stream()
                .peek(todoDTO -> todoDTO.setUsername(userDTO.getUsername()));
    }
}

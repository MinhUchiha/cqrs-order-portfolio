package com.tcbs.controller;

import com.tcbs.command.AcceptOrderCommand;
import com.tcbs.command.CancelOrderCommand;
import com.tcbs.dto.OrderDTO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private CommandGateway commandGateway;

    @PostMapping
    public CompletableFuture<String> create(@RequestBody OrderDTO dto) {
        var command = new AcceptOrderCommand(
            UUID.randomUUID().toString(), 
            dto.getUserId(),
            dto.getAction(),
            dto.getProduct(),
            dto.getQuantity());
        log.info("Executing command: {}", command);
        return commandGateway.send(command);
    }

    @PostMapping("/{id}/cancel")
    public CompletableFuture<String> cancel(@PathVariable String id) {
        var command = new CancelOrderCommand(id);
        log.info("Executing command: {}", command);
        return commandGateway.send(command);
    }
}
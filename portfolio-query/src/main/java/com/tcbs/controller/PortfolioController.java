package com.tcbs.controller;

import com.tcbs.model.PortfolioItem;
import com.tcbs.repository.PortfolioItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("/portfolio-items")
public class PortfolioController {

    private PortfolioItemRepository repository;

    @GetMapping
    public ResponseEntity<Iterable<PortfolioItem>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }
}
package com.example.coindesk.controller;

import com.example.coindesk.dto.CurrencyRequest;
import com.example.coindesk.entity.CurrencyMapping;
import com.example.coindesk.service.CurrencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/** 幣別資料表 CRUD */
@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {

    private final CurrencyService service;

    public CurrencyController(CurrencyService service) {
        this.service = service;
    }

    @GetMapping
    public List<CurrencyMapping> findAll() {
        return service.findAll();
    }

    @GetMapping("/{code}")
    public CurrencyMapping findOne(@PathVariable String code) {
        return service.findByCode(code);
    }

    @PostMapping
    public ResponseEntity<CurrencyMapping> create(@Valid @RequestBody CurrencyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{code}")
    public CurrencyMapping update(@PathVariable String code, @Valid @RequestBody CurrencyRequest request) {
        return service.update(code, request);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> delete(@PathVariable String code) {
        service.delete(code);
        return ResponseEntity.noContent().build();
    }
}

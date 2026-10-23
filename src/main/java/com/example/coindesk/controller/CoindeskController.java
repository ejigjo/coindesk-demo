package com.example.coindesk.controller;

import com.example.coindesk.dto.CoindeskResponse;
import com.example.coindesk.dto.TransformedRateResponse;
import com.example.coindesk.service.CoindeskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/coindesk")
public class CoindeskController {

    private final CoindeskService service;

    public CoindeskController(CoindeskService service) {
        this.service = service;
    }

    @GetMapping
    public CoindeskResponse original() {
        return service.getOriginal();
    }

    @GetMapping("/transformed")
    public TransformedRateResponse transformed() {
        return service.getTransformed();
    }
}

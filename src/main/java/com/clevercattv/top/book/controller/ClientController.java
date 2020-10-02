package com.clevercattv.top.book.controller;

import com.clevercattv.top.book.entity.ClientType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientController {

    @GetMapping
    public ClientType[] clients() {
        return ClientType.values();
    }

}

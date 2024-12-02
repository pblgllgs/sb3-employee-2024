package com.pblgllgs.employeeservice.controller;
/*
 *
 * @author pblgl
 * Created on 28-11-2024
 *
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class MessageController {

    @Value("${spring.boot.message}")
    private String message;

    @GetMapping("/users/message")
    public String getMessage() {
        return message;
    }
}

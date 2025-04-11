package com.example.sosbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sosbackend.dto.HelloResponse;
import com.example.sosbackend.service.HelloService;

@RestController
@RequestMapping("/api")
public class HelloController {
  private final HelloService helloService;

  public HelloController(HelloService helloService) {
    this.helloService = helloService;
  }

  @GetMapping
  public HelloResponse sayHello() {
    String message = this.helloService.getGreeting();
    return new HelloResponse(message);
    
  }
}

package com.example.demo.controller;

import com.example.demo.result.Result;
import com.example.demo.result.ResultFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public Result hello(){
        return ResultFactory.buildSuccessResult("Hello World");
    }
}

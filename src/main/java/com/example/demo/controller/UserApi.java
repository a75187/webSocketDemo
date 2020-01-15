package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @desc: -.
 * @Author: lipei
 * @CreateDate: 2019/11/28 9:24
 * @Version: 1.0
 */

@RestController
@RequestMapping(value = "/sk")
public class UserApi {
    @Resource
    private WebSocket sk;

    @GetMapping(value = "/driving")
    public void aa() throws IOException {
        sk.sendMessageAll("大家好!");
    }

    @RequestMapping(value = "/send/{userName}")
    public void aac(@PathVariable("userName") String userName) throws IOException {
        sk.sendMessageTo("hhah",userName);
    }

    @GetMapping(value = "/test")
    public void aapp() {
            System.out.println(Thread.currentThread().getName()+this);
    }
}

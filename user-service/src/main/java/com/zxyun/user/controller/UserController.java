package com.zxyun.user.controller;

import com.zxyun.user.annotation.Auth;
import com.zxyun.user.util.BResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/ping")
    @Auth
    public BResponse ping () {
        return BResponse.ok("hello world");
    }
}

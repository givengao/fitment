package com.zxyun.user.controller;

import com.zxyun.user.annotation.Auth;
import com.zxyun.user.base.BaseController;
import com.zxyun.user.base.BaseDto;
import com.zxyun.user.util.BResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    @GetMapping("/ping")
    @Auth
    public BResponse ping (@RequestBody BaseDto baseDto) {
        return BResponse.ok(baseDto);
    }

    @PostMapping("/register")
    public BResponse register () {
        return BResponse.ok();
    }

    @PostMapping("/login")
    public BResponse login () {
        return BResponse.ok();
    }

    @PostMapping("/changePwd")
    @Auth
    public BResponse changePwd () {
        return BResponse.ok();
    }
}

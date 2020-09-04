package com.learn.demotdd.controller;

import com.learn.demotdd.constant.URLConstant;
import com.learn.demotdd.service.UserService;
import com.learn.demotdd.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(URLConstant.USER_API)
public class UserController {

    private final UserService userService;

    @PostMapping
    public void save(@RequestBody UserVO userVO) {
        userService.save(userVO);
    }

    @GetMapping
    public UserVO getOne(String id) {
        return userService.getOne(id);
    }
}

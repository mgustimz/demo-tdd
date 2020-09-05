package com.learn.demotdd.controller;

import com.google.common.truth.Truth;
import com.learn.demotdd.service.UserService;
import com.learn.demotdd.vo.UserVO;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class UserControllerTest {

    private final EasyRandom easyRandom = new EasyRandom();
    private UserController userController;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    void givenRequest_whenSave_shouldCallService() {
        UserVO userVO = easyRandom.nextObject(UserVO.class);
        userController.save(userVO);
        verify(userService, times(1)).save(userVO);
    }

    @Test
    void givenRequest_whenGetOne_shouldCallService() {
        String id = easyRandom.nextObject(String.class);
        userController.getOne(id);
        verify(userService, times(1)).getOne(id);
    }

    @Test
    void givenExistingResult_whenGetOne_shouldReturnCorrectInstance() {
        String id = easyRandom.nextObject(String.class);
        when(userService.getOne(id)).thenReturn(easyRandom.nextObject(UserVO.class));
        Truth.assertThat(userController.getOne(id)).isInstanceOf(UserVO.class);
    }

}

package com.learn.demotdd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.truth.Truth;
import com.learn.demotdd.constant.URLConstant;
import com.learn.demotdd.exception.NoDataException;
import com.learn.demotdd.service.UserService;
import com.learn.demotdd.vo.UserVO;
import org.hamcrest.Matchers;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.regex.Matcher;

import static com.google.common.truth.Truth.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class UserControllerIntegrationTest {

    private final EasyRandom easyRandom = new EasyRandom();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    void givenRequest_whenSave_shouldCallService() throws Exception {
        UserVO userVO = easyRandom.nextObject(UserVO.class);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post(URLConstant.USER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userVO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
        ArgumentCaptor<UserVO> argumentCaptor = ArgumentCaptor.forClass(UserVO.class);
        verify(userService, times(1)).save(argumentCaptor.capture());
        UserVO captured = argumentCaptor.getValue();
        assertThat(userVO).isEqualTo(captured);
    }

    @Test
    void givenRequest_whenGetOne_shouldReturnCorrectResult() throws Exception {
        String id = easyRandom.nextObject(String.class);
        UserVO userVO = easyRandom.nextObject(UserVO.class);
        when(userService.getOne(id)).thenReturn(userVO);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get(URLConstant.USER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                .param("id", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userVO.getId())))
                .andExpect(jsonPath("$.firstName", is(userVO.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(userVO.getLastName())));
    }

    @Test
    void givenNotFound_whenGetOne_shouldReturnCorrectErrorMessage() throws Exception {
        String id = easyRandom.nextObject(String.class);
        when(userService.getOne(id)).thenThrow(new NoDataException("User not found"));
        mockMvc.perform(
                MockMvcRequestBuilders
                .get(URLConstant.USER_API)
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode", is(NoDataException.ERROR_CODE)))
                .andExpect(jsonPath("$.message", is("User not found")));
    }

}

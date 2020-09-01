package com.learn.demotdd.service;

import com.learn.demotdd.entity.User;
import com.learn.demotdd.exception.NoDataException;
import com.learn.demotdd.repository.UserRepository;
import com.learn.demotdd.vo.UserVO;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private final EasyRandom easyRandom = new EasyRandom();
    private UserService userService;
    private UserRepository userRepository;
    private UserVO userVO;
    private User user;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new DefaultUserService(userRepository);
    }

    @Test
    void givenNullVo_whenSave_shouldThrowException() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> userService.save(null));
        assertThat(e.getMessage()).isEqualTo("Request cannot be null");
    }

    @Test
    void givenValidVo_whenSave_shouldCallRepository() {
        prepareSave();
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void givenValidVo_whenSave_shouldCallRepositoryWithCorrectParams() {
        prepareSave();
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());
        User user = userArgumentCaptor.getValue();
        assertThat(user.getId()).isNotNull();
        assertThat(user.getFirstName()).isEqualTo(userVO.getFirstName());
        assertThat(user.getLastName()).isEqualTo(userVO.getLastName());
    }

    @Test
    void givenNullId_whenGetOne_shouldThrowException() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> userService.getOne(null));
        assertThat(e.getMessage()).isEqualTo("Request cannot be null");
    }

    @Test
    void givenValidId_whenGetOne_shouldCallRepository() {
        String id = easyRandom.nextObject(String.class);
        injectUser(id);
        userService.getOne(id);
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void givenValidIdAndResultNotAvailable_whenGetOne_shouldThrowException() {
        String id = easyRandom.nextObject(String.class);
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        Exception e = assertThrows(NoDataException.class, () -> userService.getOne(id));
        assertThat(e.getMessage()).isEqualTo("User not found");
    }

    @Test
    void givenValidIdAndResult_whenGetOne_shouldReturnCorrectVo() {
        String id = easyRandom.nextObject(String.class);
        injectUser(id);
        userVO = userService.getOne(id);
        assertThat(userVO.getId()).isEqualTo(user.getId());
        assertThat(userVO.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(userVO.getLastName()).isEqualTo(user.getLastName());
    }

    private void injectUser(String id) {
        user = easyRandom.nextObject(User.class);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
    }

    private void prepareSave() {
        userVO = easyRandom.nextObject(UserVO.class);
        userService.save(userVO);
    }

}

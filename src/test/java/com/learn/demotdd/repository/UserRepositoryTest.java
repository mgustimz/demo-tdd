package com.learn.demotdd.repository;

import com.learn.demotdd.entity.User;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import static com.google.common.truth.Truth.assertThat;

@DataJpaTest
@DirtiesContext
class UserRepositoryTest {

    private final EasyRandom easyRandom = new EasyRandom();
    @Autowired
    private UserRepository userRepository;

    @Test
    void givenUser_whenSave_shouldAddToRepo() {
        User user = easyRandom.nextObject(User.class);
        userRepository.save(user);
        assertThat(userRepository.count()).isEqualTo(1);
    }

    @Test
    void givenUser_whenFindById_shouldReturnCorrectResult() {
        User user = easyRandom.nextObject(User.class);
        userRepository.saveAndFlush(user);
        User savedUser = userRepository.getOne(user.getId());
        assertThat(user).isEqualTo(savedUser);
    }
}

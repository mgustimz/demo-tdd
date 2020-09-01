package com.learn.demotdd.service;

import com.learn.demotdd.entity.User;
import com.learn.demotdd.exception.NoDataException;
import com.learn.demotdd.repository.UserRepository;
import com.learn.demotdd.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public void save(UserVO vo) {
        validateRequest(vo);
        User user = UserVO.create(vo);
        userRepository.save(user);
    }

    @Override
    public UserVO getOne(String id) {
        validateRequest(id);
        return userRepository.findById(id)
                .map(UserVO::get)
                .orElseThrow(() -> new NoDataException("User not found"));
    }

    private void validateRequest(Object param) {
        if (null == param) {
            throw new IllegalArgumentException("Request cannot be null");
        }
    }

}

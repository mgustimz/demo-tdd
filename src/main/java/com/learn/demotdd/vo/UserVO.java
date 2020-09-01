package com.learn.demotdd.vo;

import com.learn.demotdd.entity.User;
import lombok.Data;

import java.util.UUID;

@Data
public class UserVO {

    private String id;
    private String firstName;
    private String lastName;

    public static User create(UserVO vo) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setFirstName(vo.getFirstName());
        user.setLastName(vo.getLastName());
        return user;
    }

    public static UserVO get(User user) {
        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setFirstName(user.getFirstName());
        userVO.setLastName(user.getLastName());
        return userVO;
    }

}

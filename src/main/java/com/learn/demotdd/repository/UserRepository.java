package com.learn.demotdd.repository;

import com.learn.demotdd.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}

package com.cos.springsecurity.repository;

import com.cos.springsecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByUsername(String username); // jpa 쿼리 메서드 참고.
}

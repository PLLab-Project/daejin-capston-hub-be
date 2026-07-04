package com.daejin.capstone.doamin.user.repository;

import com.daejin.capstone.doamin.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}

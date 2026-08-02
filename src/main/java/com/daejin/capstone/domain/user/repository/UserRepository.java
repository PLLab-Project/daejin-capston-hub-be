package com.daejin.capstone.domain.user.repository;

import com.daejin.capstone.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}

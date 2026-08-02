package com.daejin.capstone.doamin.auth.repository;

import com.daejin.capstone.doamin.auth.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, Long> {

}

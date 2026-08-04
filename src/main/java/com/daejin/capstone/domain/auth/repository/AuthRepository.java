package com.daejin.capstone.domain.auth.repository;

import com.daejin.capstone.domain.auth.entity.Auth;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, Long> {

  Optional<Auth> findByUuid(String uuid);
}

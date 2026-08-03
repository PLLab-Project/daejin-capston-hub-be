package com.daejin.capstone.domain.user.repository;

import com.daejin.capstone.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUuid(String uuid);

  Optional<User> findByStdNum(String stdNum);

}

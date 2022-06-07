package com.belhard.repository.user;

import com.belhard.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT id, name, second_name, email, password, role FROM users WHERE activity = true ORDER BY id ASC", nativeQuery = true)
    List<User> findAllUsers();
}

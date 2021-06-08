package com.liverday.microservices.auth.repositories;

import com.liverday.microservices.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@SuppressWarnings("unused")
public interface UsersRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u From User u where u.userName = :userName")
    User findByUserName(@Param("userName") String userName);
}

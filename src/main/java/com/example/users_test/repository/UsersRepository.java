package com.example.users_test.repository;

import com.example.users_test.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<Users, UUID> {
    @Query("select u from Users u where u.birthDate between :from and :to")
    List<Users> findByBirthDateRange(@Param("from") LocalDate from,
                                     @Param("to") LocalDate to);
}

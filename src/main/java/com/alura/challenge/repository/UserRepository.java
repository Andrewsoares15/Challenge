package com.alura.challenge.repository;

import com.alura.challenge.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM USER WHERE USER.email != 'admin@email.com.br'", nativeQuery = true)
    List<User> findAllIgnoreAdmin();

    @Query(value = "SELECT * FROM USER WHERE USER.id = :id AND USER.email != 'admin@email.com.br'", nativeQuery = true)
    Optional<User> findByIdIgnoreAdmin(@Param("id") Long id);
}

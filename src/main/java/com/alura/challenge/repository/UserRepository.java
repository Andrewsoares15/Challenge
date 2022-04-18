package com.alura.challenge.repository;

import com.alura.challenge.domain.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);

    @Query(value = "SELECT * FROM USER WHERE USER.email != 'admin@email.com.br'", nativeQuery = true)
    List<User> findAllIgnoreAdmin();

}

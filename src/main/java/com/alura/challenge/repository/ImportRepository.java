package com.alura.challenge.repository;

import com.alura.challenge.domain.entity.Importacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImportRepository extends JpaRepository<Importacao, Long> {

    List<Importacao> findAllByOrderByDateTransactionsDesc();
}

package com.alura.challenge.repository;

import com.alura.challenge.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoRepository extends JpaRepository<Transaction, Long> {
}

package com.alura.challenge.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bancoOrigem;

    private Integer agenciaOrigem;

    private String contaOrigem;

    private String bancoDestino;

    private Integer agenciaDestino;

    private String contaDestino;

    private Double valor;

    private LocalDateTime data;
}

package com.alura.challenge.domain.DTOs;

import com.alura.challenge.domain.entity.Transaction;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionResponse {

    private String bancoOrigem;

    private Integer agenciaOrigem;

    private String contaOrigem;

    private String bancoDestino;

    private Integer agenciaDestino;

    private String contaDestino;

    private Double valor;

    private LocalDateTime data;

    public TransactionResponse(Transaction transaction){
        this.bancoOrigem = transaction.getBancoOrigem();
        this.agenciaOrigem = transaction.getAgenciaOrigem();
        this.contaOrigem = transaction.getContaOrigem();
        this.bancoDestino = transaction.getBancoDestino();
        this.agenciaDestino = transaction.getAgenciaDestino();
        this.contaDestino = transaction.getContaDestino();
        this.valor = transaction.getValor();
        this.data = transaction.getData();
    }
}

package com.alura.challenge.domain.DTOs;

import com.alura.challenge.domain.entity.Importacao;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ImportationResponse {

    private LocalDateTime dateImport;

    private LocalDate dateTransactions;

    public ImportationResponse(Importacao importation){
        this.dateImport = importation.getDateImport();
        this.dateTransactions = importation.getDateTransactions();
    }
}

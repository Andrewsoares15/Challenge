package com.alura.challenge.service;

import com.alura.challenge.domain.DTOs.ImportationResponse;
import com.alura.challenge.domain.DTOs.TransactionResponse;
import com.alura.challenge.domain.entity.Importacao;
import com.alura.challenge.helper.CsvHelper;
import com.alura.challenge.repository.ImportRepository;
import com.alura.challenge.repository.TransacaoRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;
    @Autowired
    private ImportRepository importacaoRepository;
    @Autowired
    private CsvHelper csvHelper;

    public void SaveTransaction(MultipartFile file) throws IOException {
        if(file.getSize() == 0) throw new RuntimeException("Arquivo vazio!");

        if(!CsvHelper.hasCSVFormat(file)) throw new RuntimeException("Arquivo Inválido!");

        var transactions = csvHelper.csvConvert(file);
        saveImport(transactions.get(0).getData().toLocalDate());
        var saveTransactions = transactions.stream()
                .map(transaction -> transacaoRepository.save(transaction)).collect(Collectors.toList());
    }

    public void saveImport(LocalDate dataTransactions){
        var importation =  Importacao.builder().dateImport(LocalDateTime.now())
                .dateTransactions(dataTransactions).build();
        importacaoRepository.save(importation);
    }

    public List<ImportationResponse> getImports() {
        return importacaoRepository.findAllByOrderByDateTransactionsDesc().stream()
                .map(importation -> new ImportationResponse(importation)).collect(Collectors.toList());
    }
}

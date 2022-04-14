package com.alura.challenge.service;

import com.alura.challenge.domain.DTOs.TransactionResponse;
import com.alura.challenge.domain.Transaction;
import com.alura.challenge.helper.CsvHelper;
import com.alura.challenge.repository.ImportRepository;
import com.alura.challenge.repository.TransacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    private TransacaoService transacaoServiceTest;
    @Mock
    private CsvHelper cvsHelper;
    @Mock
    private TransacaoRepository transacaoRepositoryTest;
    @Mock
    private ImportRepository importRepositoryTest;
    @BeforeEach // antes de cada
    void setUp(){
        transacaoServiceTest = new TransacaoService(importRepositoryTest, cvsHelper);
    }
    @Test
    void DeveriaSalvarTransaction() throws IOException {
        var lista = transacoes();
        //dado
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream =  ClassLoader.getSystemClassLoader().getResourceAsStream("transacoes.csv");
       // InputStream inputStream = this.getClass().getResourceAsStream("/transacoes.csv");

        //quando
//        when(cvsHelper.csvConvert(inputStream)).thenReturn(lista);
       transacaoServiceTest.SaveTransaction((MultipartFile) inputStream);
        //entao
        verify(transacaoRepositoryTest).save(lista.get(0));
        verify(transacaoRepositoryTest).save(lista.get(1));
        //var transactionResponse = lista.get(0);
    }
    @Test()
    void DeveriaRetornarExceptionAoEnviarArquivoVazio() throws IOException {
        MultipartFile inputStream = (MultipartFile) this.getClass().getResourceAsStream("transacoes.csv");

       transacaoServiceTest.SaveTransaction(inputStream);
    }
    @Test
    @Disabled
    void saveImport() {
    }

    @Test
    void DeveriaListarTodasAsImports() {
        //quando
        transacaoServiceTest.getImports();
        // entao
        verify(importRepositoryTest).findAllByOrderByDateTransactionsDesc();
    }
    private List<Transaction> transacoes(){
            var transacao = Transaction.builder().bancoOrigem("BANCO DO BRASIL")
            .agenciaOrigem(1)
            .contaOrigem("00001-1")
            .bancoDestino("BANCO BRADESCO")
            .agenciaDestino(1)
            .contaDestino("00001-1")
            .valor(8000.00)
            .data(LocalDateTime.now())
            .build();
        var transacao2 = Transaction.builder().bancoOrigem("BANCO SANTANDER")
                .agenciaOrigem(1)
                .contaOrigem("00001-1")
                .bancoDestino("BANCO BRADESCO")
                .agenciaDestino(1)
                .contaDestino("00001-1")
                .valor(210.00)
                .data(LocalDateTime.now())
                .build();
        List<Transaction> lista = new ArrayList<>();
        lista.add(transacao);
        lista.add(transacao2);
        return lista;

    }
}

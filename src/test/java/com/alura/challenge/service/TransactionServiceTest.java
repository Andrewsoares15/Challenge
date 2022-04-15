package com.alura.challenge.service;

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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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
        transacaoServiceTest = new TransacaoService(transacaoRepositoryTest,importRepositoryTest, cvsHelper);
    }
    @Test
    void DeveriaSalvarTransaction() throws IOException {
        var lista = transacoes();
        //dado
        MultipartFile multipartFile = mockMultipartFile("src/test/resources/transacoes.csv");
        //quando
        when(cvsHelper.csvConvert(multipartFile)).thenReturn(lista);
       transacaoServiceTest.SaveTransaction(multipartFile);
        //entao
        verify(transacaoRepositoryTest).save(lista.get(0));
        verify(transacaoRepositoryTest).save(lista.get(1));
    }
    @Test()
    void DeveriaRetornarExceptionAoEnviarArquivoVazio() throws IOException {
        MultipartFile multipartFile = mockMultipartFile("src/test/resources/transacoesvazio.csv");

        var runtimeException = assertThrows(RuntimeException.class, () -> transacaoServiceTest.SaveTransaction(multipartFile));

        assertTrue(runtimeException.getMessage().contains("Arquivo vazio!"));
    }
    @Test
    void DeveriaRetornarExceptionAoEnviarArquivoInvalido() throws IOException {
        MultipartFile multipartFile = mockMultipartFile("src/test/resources/teste.html");

        var runtimeException = assertThrows(RuntimeException.class, () -> transacaoServiceTest.SaveTransaction(multipartFile));

        assertTrue(runtimeException.getMessage().contains("Arquivo Inválido!"));
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
    private MultipartFile mockMultipartFile(String path) throws IOException {
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        return new MockMultipartFile("file", file.getName(), "text/" +file.getName().split("[.]")[1], fileInputStream);
    }
    private List<Transaction> transacoes(){
            var transacao = Transaction.builder().bancoOrigem("BANCO DO BRASIL")
            .agenciaOrigem(0001)
            .contaOrigem("00001-1")
            .bancoDestino("BANCO BRADESCO")
            .agenciaDestino(0001)
            .contaDestino("00001-1")
            .valor(8000.00)
            .data(LocalDateTime.parse("2022-01-01T07:30:00"))
            .build();
        var transacao2 = Transaction.builder().bancoOrigem("BANCO SANTANDER")
                .agenciaOrigem(0001)
                .contaOrigem("00001-1")
                .bancoDestino("BANCO BRADESCO")
                .agenciaDestino(0001)
                .contaDestino("00001-1")
                .valor(210.00)
                .data(LocalDateTime.parse("2022-01-01T08:12:00"))
                .build();

        List<Transaction> lista = new ArrayList<>();
        lista.add(transacao);
        lista.add(transacao2);
        return lista;

    }
}

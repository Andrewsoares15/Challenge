package com.alura.challenge.repository;

import com.alura.challenge.domain.entity.Importacao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ImportRepositoryTest {

    @Autowired
    private ImportRepository underTest;

    @Test
    void deveriaRetornarUmaListaDeImportsOrdenadaPorDateTransactions() {
            // dado
        var imports = new ArrayList<Importacao>();
        var importation2 = Importacao.builder()
                .dateImport(LocalDateTime.now())
                .dateTransactions(LocalDate.now().plusDays(2l)).build();
        var importation1 = Importacao.builder()
                .dateImport(LocalDateTime.now())
                .dateTransactions(LocalDate.now().plusDays(1l)).build();
        imports.add(importation1);
        imports.add(importation2);

        underTest.save(importation2);
        underTest.save(importation1);

        // quando
        List<Importacao> expectList = underTest.findAllByOrderByDateTransactionsDesc();

        //entao
        assertThat(expectList.get(0)).isEqualTo(importation2);
    }
}
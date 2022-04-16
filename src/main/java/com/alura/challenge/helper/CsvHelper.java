package com.alura.challenge.helper;

import com.alura.challenge.domain.entity.Transaction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Component
public class CsvHelper {

    private String[] HEADERs = {"bancoOrigem", "agenciaOrigem", "contaOrigem", "bancoDestino", "agenciaDestino",
            "contaDestino", "valor", "data"};

    public static boolean hasCSVFormat(MultipartFile file) {
        if (Objects.equals(file.getContentType(), "text/csv")) {
            return true;
        }
        return false;
    }
    public  List<Transaction> csvConvert(MultipartFile is) throws IOException {
        List<Transaction> transacoes = new ArrayList<>();

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is.getInputStream(), "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.EXCEL.withHeader(HEADERs))) {

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            var dataTransacoes = convertDate(csvRecords);

            for (CSVRecord csvRecord : csvRecords) {

                var equalsDate = compareDate(dataTransacoes, csvRecord.get("data").split("T")[0]);
                var transacao = validationTransaction(csvRecord);

                if(equalsDate && transacao != null){
                    transacoes.add(transacao);
                }
            }
        }
        return transacoes;
    }
    private static LocalDate convertDate(Iterable<CSVRecord> csvRecords){
        String dataText = csvRecords.iterator().next().get("data").split("T")[0];
        return LocalDate.parse(dataText);
    }
    private static Boolean compareDate(LocalDate firstDateInCsvFile, String date){
        return firstDateInCsvFile.compareTo(LocalDate.parse(date)) == 0;
    }
    private Transaction validationTransaction(CSVRecord csvRecord){
        for (String header: this.HEADERs) {
            if (csvRecord.get(header).isBlank()) return null;
        }
        return Transaction.builder().bancoOrigem(csvRecord.get("bancoOrigem"))
                .agenciaOrigem(Integer.valueOf(csvRecord.get("agenciaOrigem")))
                .contaOrigem(csvRecord.get("contaOrigem"))
                .bancoDestino(csvRecord.get("bancoDestino"))
                .agenciaDestino(Integer.valueOf(csvRecord.get("agenciaDestino")))
                .contaDestino(csvRecord.get("contaDestino"))
                .valor(Double.parseDouble(csvRecord.get("valor")))
                .data(LocalDateTime.parse(csvRecord.get("data")))
                .build();
    }
}

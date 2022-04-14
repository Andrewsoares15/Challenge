package com.alura.challenge.controller;

import com.alura.challenge.domain.DTOs.ImportationResponse;
import com.alura.challenge.domain.DTOs.TransactionResponse;
import com.alura.challenge.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class TransacaoController {

    @Autowired
    public TransacaoService transacaoService;

    @PostMapping(value = "/insertFile" )
    public ResponseEntity<List<TransactionResponse>> postFile(@RequestParam("file") MultipartFile file) throws IOException {
        var transactions= transacaoService.SaveTransaction(file);

        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/getImports")
    public ResponseEntity<List<ImportationResponse>> getImports(){
        var imports = transacaoService.getImports();

        return ResponseEntity.ok(imports);
    }
}

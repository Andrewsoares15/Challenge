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
    public ResponseEntity<List<TransactionResponse>> postFile(@RequestPart("file") MultipartFile file) throws IOException {
       transacaoService.SaveTransaction(file);

        return ResponseEntity.created(null).build();
    }

    @GetMapping("/getImports")
    public ResponseEntity<List<ImportationResponse>> getImports(){
        var imports = transacaoService.getImports();

        return ResponseEntity.ok(imports);
    }
}

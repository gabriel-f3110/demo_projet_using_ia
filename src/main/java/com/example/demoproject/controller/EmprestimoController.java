package com.example.demoproject.controller;

import com.example.demoproject.model.dto.DevolucaoRequestDTO;
import com.example.demoproject.model.dto.EmprestimoDTO;
import com.example.demoproject.model.dto.EmprestimoRequestDTO;
import com.example.demoproject.model.dto.RenovacaoRequestDTO;
import com.example.demoproject.service.EmprestimoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @PostMapping
    public ResponseEntity<EmprestimoDTO> realizarEmprestimo(@Valid @RequestBody EmprestimoRequestDTO emprestimoRequestDTO) {
        EmprestimoDTO novoEmprestimo = emprestimoService.realizarEmprestimo(emprestimoRequestDTO);
        return new ResponseEntity<>(novoEmprestimo, HttpStatus.CREATED);
    }

    @PostMapping("/renovar")
    public ResponseEntity<EmprestimoDTO> renovarEmprestimo(@Valid @RequestBody
                                                               RenovacaoRequestDTO renovacaoRequestDTO) {
        EmprestimoDTO emprestimoRenovado = emprestimoService.renovarEmprestimo(renovacaoRequestDTO);
        return ResponseEntity.ok(emprestimoRenovado);
    }

    @PostMapping("/devolver")
    public ResponseEntity<EmprestimoDTO> realizarDevolucao(@Valid @RequestBody DevolucaoRequestDTO devolucaoRequestDTO) {
        EmprestimoDTO emprestimoDevolvido = emprestimoService.realizarDevolucao(devolucaoRequestDTO);
        return ResponseEntity.ok(emprestimoDevolvido);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmprestimoDTO> buscarEmprestimoPorId(@PathVariable Long id) {
        EmprestimoDTO emprestimoDTO = emprestimoService.buscarEmprestimoPorId(id);
        return emprestimoDTO != null ? ResponseEntity.ok(emprestimoDTO) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<EmprestimoDTO>> listarEmprestimos() {
        return ResponseEntity.ok(emprestimoService.listarEmprestimos());
    }
}
package com.example.demoproject.controller;

import com.example.demoproject.model.dto.AlunoDTO;
import com.example.demoproject.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @PostMapping
    public ResponseEntity<AlunoDTO
            > criarAluno(@Valid @RequestBody AlunoDTO alunoDTO) {
        AlunoDTO novoAluno = alunoService.criarAluno(alunoDTO);
        return new ResponseEntity<>(novoAluno, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> buscarAlunoPorId(@PathVariable Long id) {
        AlunoDTO alunoDTO = alunoService.buscarAlunoPorId(id);
        return alunoDTO != null ? ResponseEntity.ok(alunoDTO) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<AlunoDTO>> listarAlunos() {
        return ResponseEntity.ok(alunoService.listarAlunos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoDTO> atualizarAluno(@PathVariable Long id, @Valid @RequestBody AlunoDTO alunoDTO) {
        AlunoDTO alunoAtualizado = alunoService.atualizarAluno(id, alunoDTO);
        return alunoAtualizado != null ? ResponseEntity.ok(alunoAtualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAluno(@PathVariable Long id) {
        alunoService.deletarAluno(id);
        return ResponseEntity.noContent().build();
    }
}
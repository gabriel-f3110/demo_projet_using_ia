package com.example.demoproject.controller;

import com.example.demoproject.model.dto.LivroDTO;
import com.example.demoproject.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @PostMapping
    public ResponseEntity<LivroDTO> criarLivro(@Valid @RequestBody LivroDTO livroDTO) {
        LivroDTO novoLivro = livroService.criarLivro(livroDTO);
        return new ResponseEntity<>(novoLivro, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroDTO> buscarLivroPorId(@PathVariable Long id) {
        LivroDTO livroDTO = livroService.buscarLivroPorId(id);
        return livroDTO != null ? ResponseEntity.ok(livroDTO) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<LivroDTO>> listarLivros() {
        return ResponseEntity.ok(livroService.listarLivros());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroDTO> atualizarLivro(@PathVariable Long id, @Valid @RequestBody LivroDTO livroDTO) {
        LivroDTO livroAtualizado = livroService.atualizarLivro(id, livroDTO);
        return livroAtualizado != null ? ResponseEntity.ok(livroAtualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Long id) {
        livroService.deletarLivro(id);
        return ResponseEntity.noContent().build();
    }
}
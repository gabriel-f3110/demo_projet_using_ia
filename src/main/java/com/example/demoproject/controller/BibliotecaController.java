package com.example.demoproject.controller;

import com.example.demoproject.model.dto.BibliotecaDTO;
import com.example.demoproject.service.BibliotecaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bibliotecas")
public class BibliotecaController {

    @Autowired
    private BibliotecaService bibliotecaService;

    @PostMapping
    public ResponseEntity<BibliotecaDTO> criarBiblioteca(@Valid @RequestBody BibliotecaDTO bibliotecaDTO) {
        BibliotecaDTO novaBiblioteca = bibliotecaService.criarBiblioteca(bibliotecaDTO);
        return new ResponseEntity<>(novaBiblioteca, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BibliotecaDTO> buscarBibliotecaPorId(@PathVariable Long id) {
        BibliotecaDTO bibliotecaDTO = bibliotecaService.buscarBibliotecaPorId(id);
        return bibliotecaDTO != null ? ResponseEntity.ok(bibliotecaDTO) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<BibliotecaDTO>> listarBibliotecas() {
        return ResponseEntity.ok(bibliotecaService.listarBibliotecas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BibliotecaDTO> atualizarBiblioteca(@PathVariable Long id, @Valid @RequestBody BibliotecaDTO bibliotecaDTO) {
        BibliotecaDTO bibliotecaAtualizada = bibliotecaService.atualizarBiblioteca(id, bibliotecaDTO);
        return bibliotecaAtualizada != null ? ResponseEntity.ok(bibliotecaAtualizada) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarBiblioteca(@PathVariable Long id) {
        bibliotecaService.deletarBiblioteca(id);
        return ResponseEntity.noContent().build();
    }
}
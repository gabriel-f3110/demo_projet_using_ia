package com.example.demoproject.controller;

import com.example.demoproject.model.dto.ResponsavelEmprestimoDTO;
import com.example.demoproject.service.ResponsavelEmprestimoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/responsaveis")
public class ResponsavelEmprestimoController {

    @Autowired
    private ResponsavelEmprestimoService responsavelEmprestimoService;

    @PostMapping
    public ResponseEntity<ResponsavelEmprestimoDTO> criarResponsavel(@Valid @RequestBody ResponsavelEmprestimoDTO responsavelEmprestimoDTO) {
        ResponsavelEmprestimoDTO novoResponsavel = responsavelEmprestimoService.criarResponsavel(responsavelEmprestimoDTO);
        return new ResponseEntity<>(novoResponsavel, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsavelEmprestimoDTO> buscarResponsavelPorId(@PathVariable Long id) {
        ResponsavelEmprestimoDTO responsavelDTO = responsavelEmprestimoService.buscarResponsavelPorId(id);
        return responsavelDTO != null ? ResponseEntity.ok(responsavelDTO) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<ResponsavelEmprestimoDTO>> listarResponsaveis() {
        return ResponseEntity.ok(responsavelEmprestimoService.listarResponsaveis());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsavelEmprestimoDTO> atualizarResponsavel(@PathVariable Long id, @Valid @RequestBody ResponsavelEmprestimoDTO responsavelEmprestimoDTO) {
        ResponsavelEmprestimoDTO responsavelAtualizado = responsavelEmprestimoService.atualizarResponsavel(id, responsavelEmprestimoDTO);
        return responsavelAtualizado != null ? ResponseEntity.ok(responsavelAtualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarResponsavel(@PathVariable Long id) {
        responsavelEmprestimoService.deletarResponsavel(id);
        return ResponseEntity.noContent().build();
    }
}
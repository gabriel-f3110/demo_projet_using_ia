package com.example.demoproject.service;

import com.example.demoproject.model.Biblioteca;
import com.example.demoproject.model.dto.BibliotecaDTO;
import com.example.demoproject.repository.BibliotecaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BibliotecaService {

    @Autowired
    private BibliotecaRepository bibliotecaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public BibliotecaDTO criarBiblioteca(BibliotecaDTO bibliotecaDTO) {
        Biblioteca biblioteca = modelMapper.map(bibliotecaDTO, Biblioteca.class);
        Biblioteca bibliotecaSalva = bibliotecaRepository.save(biblioteca);
        return modelMapper.map(bibliotecaSalva, BibliotecaDTO.class);
    }

    public BibliotecaDTO buscarBibliotecaPorId(Long id) {
        return bibliotecaRepository.findById(id)
                .map(biblioteca -> modelMapper.map(biblioteca, BibliotecaDTO.class))
                .orElse(null); // Tratar caso não encontre
    }

    public List<BibliotecaDTO> listarBibliotecas() {
        return bibliotecaRepository.findAll()
                .stream()
                .map(biblioteca -> modelMapper.map(biblioteca, BibliotecaDTO.class))
                .toList();
    }

    public BibliotecaDTO atualizarBiblioteca(Long id, BibliotecaDTO bibliotecaDTO) {
        return bibliotecaRepository.findById(id)
                .map(biblioteca -> {
                    modelMapper.map(bibliotecaDTO, biblioteca);
                    Biblioteca bibliotecaAtualizada = bibliotecaRepository.save(biblioteca);
                    return modelMapper.map(bibliotecaAtualizada, BibliotecaDTO.class);
                })
                .orElse(null);
    }

    public void deletarBiblioteca(Long id) {
        bibliotecaRepository.deleteById(id);
    }
}
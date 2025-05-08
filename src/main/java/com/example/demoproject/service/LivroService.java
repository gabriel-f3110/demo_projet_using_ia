package com.example.demoproject.service;

import com.example.demoproject.model.Livro;
import com.example.demoproject.model.dto.LivroDTO;
import com.example.demoproject.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private ModelMapper modelMapper;


    public LivroDTO criarLivro(LivroDTO livroDTO) {
        Livro livro = modelMapper.map(livroDTO, Livro.class);
        Livro livroSalvo = livroRepository.save(livro);
        return modelMapper.map(livroSalvo, LivroDTO.class);
    }

    public LivroDTO buscarLivroPorId(Long id) {
        return livroRepository.findById(id)
                .map(livro -> modelMapper.map(livro, LivroDTO.class))
                .orElse(null); // Tratar caso não encontre
    }

    public Livro buscarEntidadeLivroPorId(Long id) {
        return livroRepository.findById(id)
                .orElse(null); // Tratar caso não encontre
    }

    public List<LivroDTO> listarLivros() {
        return livroRepository.findAll().stream()
                .map(livro -> modelMapper.map(livro, LivroDTO.class))
                .collect(Collectors.toList());
    }

    public LivroDTO atualizarLivro(Long id, LivroDTO livroDTO) {
        return livroRepository.findById(id)
                .map(livro -> {
                    modelMapper.map(livroDTO, livro);
                    Livro livroAtualizado = livroRepository.save(livro);
                    return modelMapper.map(livroAtualizado, LivroDTO.class);
                })
                .orElse(null); // Tratar caso não encontre
    }

    public void deletarLivro(Long id) {
        livroRepository.deleteById(id);
    }

    public void decrementarQuantidade(Livro livro) {
        if (livro.getQuantidadeDisponivel() > 0) {
            livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() - 1);
            livroRepository.save(livro);
        } else {
            throw new RuntimeException("Livro indisponível para empréstimo.");
        }
    }

    public void incrementarQuantidade(Livro livro) {
        livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() + 1);
        livroRepository.save(livro);
    }
}
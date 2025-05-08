package com.example.demoproject.service;

import com.example.demoproject.model.ResponsavelEmprestimo;
import com.example.demoproject.model.dto.ResponsavelEmprestimoDTO;
import com.example.demoproject.repository.ResponsavelEmprestimoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponsavelEmprestimoService {

    @Autowired
    private ResponsavelEmprestimoRepository responsavelEmprestimoRepository;

    @Autowired
    private ModelMapper modelMapper;


    public ResponsavelEmprestimoDTO criarResponsavel(ResponsavelEmprestimoDTO responsavelEmprestimoDTO) {
        ResponsavelEmprestimo responsavel = modelMapper.map(responsavelEmprestimoDTO, ResponsavelEmprestimo.class);
        ResponsavelEmprestimo responsavelSalvo = responsavelEmprestimoRepository.save(responsavel);
        return modelMapper.map(responsavelSalvo, ResponsavelEmprestimoDTO.class);
    }

    public ResponsavelEmprestimoDTO buscarResponsavelPorId(Long id) {
        return responsavelEmprestimoRepository.findById(id)
                .map(responsavel -> modelMapper.map(responsavel, ResponsavelEmprestimoDTO.class))
                .orElse(null); // Tratar caso não encontre
    }

    public ResponsavelEmprestimo buscarResponsavelEntidadePorId(Long id) {
        return responsavelEmprestimoRepository.findById(id)
                .orElse(null); // Tratar caso não encontre
    }

    public List<ResponsavelEmprestimoDTO> listarResponsaveis() {
        return responsavelEmprestimoRepository.findAll().stream()
                .map(responsavel -> modelMapper.map(responsavel, ResponsavelEmprestimoDTO.class))
                .collect(Collectors.toList());
    }

    public ResponsavelEmprestimoDTO atualizarResponsavel(Long id, ResponsavelEmprestimoDTO responsavelEmprestimoDTO) {
        return responsavelEmprestimoRepository.findById(id)
                .map(responsavel -> {
                    modelMapper.map(responsavelEmprestimoDTO, responsavel);
                    ResponsavelEmprestimo responsavelAtualizado = responsavelEmprestimoRepository.save(responsavel);
                    return modelMapper.map(responsavelAtualizado, ResponsavelEmprestimoDTO.class);
                })
                .orElse(null); // Tratar caso não encontre
    }

    public void deletarResponsavel(Long id) {
        responsavelEmprestimoRepository.deleteById(id);
    }
}
package com.example.demoproject.service;

import com.example.demoproject.model.Aluno;
import com.example.demoproject.model.dto.AlunoDTO;
import com.example.demoproject.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AlunoDTO criarAluno(AlunoDTO alunoDTO) {
        if (alunoRepository.existsByMatricula(alunoDTO.getMatricula())) {
            throw new RuntimeException("Matrícula já cadastrada.");
        }
        Aluno aluno = modelMapper.map(alunoDTO, Aluno.class);
        Aluno alunoSalvo = alunoRepository.save(aluno);
        return modelMapper.map(alunoSalvo, AlunoDTO.class);
    }

    public AlunoDTO buscarAlunoPorId(Long id) {
        return alunoRepository.findById(id)
                .map(aluno -> modelMapper.map(aluno, AlunoDTO.class))
                .orElse(null); // Tratar caso não encontre
    }

    public List<AlunoDTO> listarAlunos() {
        return alunoRepository.findAll().stream()
                .map(aluno -> modelMapper.map(aluno, AlunoDTO.class))
                .collect(Collectors.toList());
    }

    public AlunoDTO atualizarAluno(Long id, AlunoDTO alunoDTO) {
        return alunoRepository.findById(id)
                .map(aluno -> {
                    if (!aluno.getMatricula().equals(alunoDTO.getMatricula())
                            && alunoRepository.existsByMatricula(alunoDTO.getMatricula())) {
                        throw new RuntimeException("Matrícula já cadastrada.");
                    }
                    modelMapper.map(alunoDTO, aluno);
                    Aluno alunoAtualizado = alunoRepository.save(aluno);
                    return modelMapper.map(alunoAtualizado, AlunoDTO.class);
                })
                .orElse(null); // Tratar caso não encontre
    }

    public void deletarAluno(Long id) {
        alunoRepository.deleteById(id);
    }

    public Aluno buscarAlunoEntidade(Long alunoId) {
        return alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com o ID: " + alunoId));
    }
}
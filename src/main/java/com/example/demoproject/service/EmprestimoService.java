package com.example.demoproject.service;

import com.example.demoproject.model.Aluno;
import com.example.demoproject.model.Emprestimo;
import com.example.demoproject.model.Livro;
import com.example.demoproject.model.ResponsavelEmprestimo;
import com.example.demoproject.model.dto.DevolucaoRequestDTO;
import com.example.demoproject.model.dto.EmprestimoDTO;
import com.example.demoproject.model.dto.EmprestimoRequestDTO;
import com.example.demoproject.model.dto.RenovacaoRequestDTO;
import com.example.demoproject.repository.EmprestimoRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private LivroService livroService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private ResponsavelEmprestimoService responsavelEmprestimoService;

    @Autowired
    private ModelMapper modelMapper;


    @Value("${biblioteca.prazo-emprestimo}")
    private Long prazoEmprestimoDias;

    @Value("${biblioteca.valor-multa-diaria}")
    private Double valorMultaDiaria;

    @Transactional
    public EmprestimoDTO realizarEmprestimo(EmprestimoRequestDTO emprestimoRequestDTO) {
        Aluno aluno = alunoService.buscarAlunoEntidade(emprestimoRequestDTO.getAlunoId());
        Livro livro = livroService.buscarEntidadeLivroPorId(emprestimoRequestDTO.getLivroId());
        ResponsavelEmprestimo responsavel = responsavelEmprestimoService
                .buscarResponsavelEntidadePorId(emprestimoRequestDTO.getResponsavelEmprestimoId());

        if (livro == null || livro.getQuantidadeDisponivel() <= 0) {
            throw new RuntimeException("Livro não disponível para empréstimo.");
        }

        if (emprestimoRepository.findByAlunoAndDataDevolucaoRealIsNull(aluno).isPresent()) {
            throw new RuntimeException("O aluno já possui um livro emprestado.");
        }

        livroService.decrementarQuantidade(livro);

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setLivro(livro);
        emprestimo.setAluno(aluno);
        emprestimo.setDataDevolucaoPrevista(LocalDate.now().plusDays(prazoEmprestimoDias));
        emprestimo.setResponsavelEmprestimo(responsavel);

        Emprestimo emprestimoSalvo = emprestimoRepository.save(emprestimo);
        return modelMapper.map(emprestimoSalvo, EmprestimoDTO.class);
    }

    @Transactional
    public EmprestimoDTO renovarEmprestimo(RenovacaoRequestDTO renovacaoRequestDTO) {
        Emprestimo emprestimo = emprestimoRepository.findById(renovacaoRequestDTO.getEmprestimoId())
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado."));
        ResponsavelEmprestimo responsavel =
                responsavelEmprestimoService.buscarResponsavelEntidadePorId(renovacaoRequestDTO.getResponsavelEmprestimoId());

        if (emprestimo.getDataDevolucaoReal() != null) {
            throw new RuntimeException("Este empréstimo já foi finalizado.");
        }

        if (emprestimo.getRenovado()) {
            throw new RuntimeException("Este empréstimo já foi renovado.");
        }

        emprestimo.setDataDevolucaoPrevista(emprestimo.getDataDevolucaoPrevista().plusDays(prazoEmprestimoDias));
        emprestimo.setRenovado(true);
        emprestimo.setResponsavelEmprestimo(responsavel);

        Emprestimo emprestimoAtualizado = emprestimoRepository.save(emprestimo);
        return modelMapper.map(emprestimoAtualizado, EmprestimoDTO.class);
    }

    @Transactional
    public EmprestimoDTO realizarDevolucao(DevolucaoRequestDTO devolucaoRequestDTO) {
        Emprestimo emprestimo = emprestimoRepository.findById(devolucaoRequestDTO.getEmprestimoId())
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado."));

        if (emprestimo.getDataDevolucaoReal() != null) {
            throw new RuntimeException("Este empréstimo já foi devolvido.");
        }

        emprestimo.setDataDevolucaoReal(LocalDate.now());
        Livro livro = emprestimo.getLivro();
        livroService.incrementarQuantidade(livro);

        if (emprestimo.getDataDevolucaoReal().isAfter(emprestimo.getDataDevolucaoPrevista())) {
            long diasAtraso = ChronoUnit.DAYS.between(emprestimo.getDataDevolucaoPrevista(), emprestimo.getDataDevolucaoReal());
            emprestimo.setMulta(diasAtraso * valorMultaDiaria);
        } else {
            emprestimo.setMulta(0.0);
        }

        Emprestimo emprestimoAtualizado = emprestimoRepository.save(emprestimo);
        return modelMapper.map(emprestimoAtualizado, EmprestimoDTO.class);
    }

    public EmprestimoDTO buscarEmprestimoPorId(Long id) {
        return emprestimoRepository.findById(id)
                .map(emprestimo -> modelMapper.map(emprestimo, EmprestimoDTO.class))
                .orElse(null); // Tratar caso não encontre
    }

    public List<EmprestimoDTO> listarEmprestimos() {
        return emprestimoRepository.findAll().stream()
                .map(emprestimo -> modelMapper.map(emprestimo, EmprestimoDTO.class))
                .collect(Collectors.toList());
    }

    // Tarefa agendada para verificar empréstimos em atraso e calcular multas (executado diariamente)
    @Scheduled(fixedDelay = 5 * 60 * 1000)
    @Transactional
    public void verificarEmprestimosAtrasados() {
        LocalDate hoje = LocalDate.now();
        List<Emprestimo> emprestimosAtrasados = emprestimoRepository
                .findByDataDevolucaoRealIsNullAndDataDevolucaoPrevistaBefore(hoje);

        for (Emprestimo emprestimo : emprestimosAtrasados) {
            System.out.println("Verificação de emprestimo atrasado.");
            if (emprestimo.getMulta() == null || emprestimo.getMulta() == 0.0) {
                long diasAtraso = ChronoUnit.DAYS.between(emprestimo.getDataDevolucaoPrevista(), hoje);
                emprestimo.setMulta(diasAtraso * valorMultaDiaria);
                emprestimoRepository.save(emprestimo);
            }
        }
    }
}
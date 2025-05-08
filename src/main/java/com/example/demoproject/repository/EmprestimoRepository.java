package com.example.demoproject.repository;

import com.example.demoproject.model.Aluno;
import com.example.demoproject.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    Optional<Emprestimo> findByAlunoAndDataDevolucaoRealIsNull(Aluno aluno);

    //@Query(value = "SELECT * FROM emprestimo WHERE data_devolucao_real is null AND data_devolucao_prevista <= :now", nativeQuery = true)
    List<Emprestimo> findByDataDevolucaoRealIsNullAndDataDevolucaoPrevistaBefore(LocalDate now);
}
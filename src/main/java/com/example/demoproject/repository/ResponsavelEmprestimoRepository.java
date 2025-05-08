package com.example.demoproject.repository;

import com.example.demoproject.model.ResponsavelEmprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponsavelEmprestimoRepository extends JpaRepository<ResponsavelEmprestimo, Long> {
}
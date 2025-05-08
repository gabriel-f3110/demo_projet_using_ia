package com.example.demoproject.model.dto;

import lombok.Data;

public class EmprestimoRequestDTO {
    private Long livroId;
    private Long alunoId;
    private Long responsavelEmprestimoId;

    public Long getLivroId() {

        return livroId;
    }

    public void setLivroId(Long livroId) {
        this.livroId = livroId;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }

    public Long getResponsavelEmprestimoId() {
        return responsavelEmprestimoId;
    }

    public void setResponsavelEmprestimoId(Long responsavelEmprestimoId) {
        this.responsavelEmprestimoId = responsavelEmprestimoId;
    }
}
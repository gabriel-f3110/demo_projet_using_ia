package com.example.demoproject.model.dto;

public class RenovacaoRequestDTO {
    private Long emprestimoId;
    private Long responsavelEmprestimoId;

    public Long getEmprestimoId() {
        return emprestimoId;
    }

    public void setEmprestimoId(Long emprestimoId) {
        this.emprestimoId = emprestimoId;
    }

    public Long getResponsavelEmprestimoId() {
        return responsavelEmprestimoId;
    }

    public void setResponsavelEmprestimoId(Long responsavelEmprestimoId) {
        this.responsavelEmprestimoId = responsavelEmprestimoId;
    }
}
package com.example.demoproject.model.dto;

import lombok.Data;

public class DevolucaoRequestDTO {
    private Long emprestimoId;

    public Long getEmprestimoId() {
        return emprestimoId;
    }

    public void setEmprestimoId(Long emprestimoId) {
        this.emprestimoId = emprestimoId;
    }
}
package br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatusInscricaoPatchRequest {
    @NotNull(message = "O ID do novo status é obrigatório.")
    private Long statusId;

    private String observacao;
}
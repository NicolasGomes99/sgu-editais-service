package br.edu.ufape.sguEditaisService.comunicacao.dto.edital;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AtualizarStatusRequest {
    @NotNull(message = "O ID do novo status é obrigatório.")
    private Long statusId;
}
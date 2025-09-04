package br.edu.ufape.sguEditaisService.comunicacao.dto.etapa;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class EtapaReorderRequest {
    @NotEmpty(message = "A lista de IDs das etapas não pode ser vazia.")
    private List<Long> idsEtapasEmOrdem;
}
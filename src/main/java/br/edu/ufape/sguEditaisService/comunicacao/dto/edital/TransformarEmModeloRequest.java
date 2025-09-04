package br.edu.ufape.sguEditaisService.comunicacao.dto.edital;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TransformarEmModeloRequest {
    @NotBlank(message = "O nome para o novo modelo é obrigatório.")
    private String nomeModelo;
    private String descricaoModelo;
}
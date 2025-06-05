package br.edu.ufape.sguEditaisService.comunicacao.dto.etapa;

import br.edu.ufape.sguEditaisService.models.etapa;
import br.edu.ufape.sguEditaisService.models.enums.statusEtapa;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class EtapaRequest {
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private boolean obrigatoria;
    private int ordem;
    private statusEtapa status;

    public etapa convertToEntity(EtapaRequest request, ModelMapper modelMapper) {
        return modelMapper.map(request, etapa.class);
    }
}

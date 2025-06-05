package br.edu.ufape.sguEditaisService.comunicacao.dto.historicoEtapaInscricao;

import br.edu.ufape.sguEditaisService.models.HistoricoEtapaInscricao;
import br.edu.ufape.sguEditaisService.models.enums.status;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class HistoricoEtapaInscricaoRequest {
    @NotNull(message = "Data da ação é obrigatória")
    private Date dataAacao;
    @NotNull(message = "Status é obrigatório")
    private status status;
    @NotBlank(message = "Descrição é obrigatória")
    private String observação;

    public HistoricoEtapaInscricao convertToEntity(HistoricoEtapaInscricaoRequest request, ModelMapper modelMapper) {
        return modelMapper.map(request, HistoricoEtapaInscricao.class);
    }
}

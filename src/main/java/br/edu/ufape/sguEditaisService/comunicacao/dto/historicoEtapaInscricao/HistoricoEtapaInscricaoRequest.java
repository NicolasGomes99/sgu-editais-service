package br.edu.ufape.sguEditaisService.comunicacao.dto.historicoEtapaInscricao;

import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao.InscricaoRequest;
import br.edu.ufape.sguEditaisService.models.HistoricoEtapaInscricao;
import br.edu.ufape.sguEditaisService.models.StatusPersonalizado;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;
import java.util.Date;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class HistoricoEtapaInscricaoRequest {
    @NotNull(message = "Data da ação é obrigatória")
    private Date dataAacao;
    @NotBlank(message = "Descrição é obrigatória")
    private String observacao;
    private Long statusPersonalizadoId;
    private Long inscricaoId;
    private Long etapaId;

    public HistoricoEtapaInscricao convertToEntity(HistoricoEtapaInscricaoRequest request, ModelMapper modelMapper) {
        return modelMapper.map(request, HistoricoEtapaInscricao.class);
    }
}

package br.edu.ufape.sguEditaisService.comunicacao.dto.historicoEtapaInscricao;

import br.edu.ufape.sguEditaisService.models.HistoricoEtapaInscricao;
import lombok.*;
import org.modelmapper.ModelMapper;
import java.util.Date;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class HistoricoEtapaInscricaoRequest {
    private String observacao;
    private Date dataAcao;

    private Long etapaId;
    private Long inscricaoId;
    private Long statusId;

    public HistoricoEtapaInscricao convertToEntity(HistoricoEtapaInscricaoRequest request, ModelMapper modelMapper) {
        return modelMapper.map(request, HistoricoEtapaInscricao.class);
    }
}

package br.edu.ufape.sguEditaisService.comunicacao.dto.historicoEtapaInscricao;

import br.edu.ufape.sguEditaisService.models.Etapa;
import br.edu.ufape.sguEditaisService.models.HistoricoEtapaInscricao;
import br.edu.ufape.sguEditaisService.models.Inscricao;
import br.edu.ufape.sguEditaisService.models.StatusPersonalizado;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Date;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class HistoricoEtapaInscricaoRequest {
    private String observacao;

    private Long etapaId;
    private Long inscricaoId;
    private Long statusId;

    public HistoricoEtapaInscricao convertToEntity(HistoricoEtapaInscricaoRequest request, ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        HistoricoEtapaInscricao entity = modelMapper.map(request, HistoricoEtapaInscricao.class);

        if (request.getEtapaId() != null) {
            Etapa etapa = new Etapa();
            etapa.setId(request.getEtapaId());
            entity.setEtapa(etapa);
        }

        if (request.getInscricaoId() != null) {
            Inscricao inscricao = new Inscricao();
            inscricao.setId(request.getInscricaoId());
            entity.setInscricao(inscricao);
        }

        if (request.getStatusId() != null) {
            StatusPersonalizado status = new StatusPersonalizado();
            status.setId(request.getStatusId());
            entity.setStatusPersonalizado(status);
        }

        return entity;
    }
}

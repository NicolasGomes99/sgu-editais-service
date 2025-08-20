package br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao;

import br.edu.ufape.sguEditaisService.models.Edital;
import br.edu.ufape.sguEditaisService.models.Inscricao;
import br.edu.ufape.sguEditaisService.models.StatusPersonalizado;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.UUID;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class InscricaoRequest {

    private Long editalId;
    private Long statusAtualId;

    public Inscricao convertToEntity(InscricaoRequest request, ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Inscricao entity = modelMapper.map(request, Inscricao.class);

        if (request.getEditalId() != null) {
            Edital edital = new Edital();
            edital.setId(request.getEditalId());
            entity.setEdital(edital);
        }

        if (request.getStatusAtualId() != null) {
            StatusPersonalizado status = new StatusPersonalizado();
            status.setId(request.getStatusAtualId());
            entity.setStatusAtual(status);
        }

        return entity;
    }
}

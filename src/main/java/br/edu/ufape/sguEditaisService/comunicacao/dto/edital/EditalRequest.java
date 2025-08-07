package br.edu.ufape.sguEditaisService.comunicacao.dto.edital;

import br.edu.ufape.sguEditaisService.models.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class EditalRequest {
    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;

    private LocalDateTime dataPublicacao;
    private LocalDateTime inicioInscricao;
    private LocalDateTime fimIncricao;

    private Long statusAtualId;
    private Long tipoEditalId;

    public Edital convertToEntity(EditalRequest request, ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Edital entity = modelMapper.map(request, Edital.class);

        if (request.getStatusAtualId() != null) {
            StatusPersonalizado status = new StatusPersonalizado();
            status.setId(request.getStatusAtualId());
            entity.setStatusAtual(status);
        }

        if (request.getTipoEditalId() != null) {
            TipoEdital tipoEdital = new TipoEdital();
            tipoEdital.setId(request.getTipoEditalId());
            entity.setTipoEdital(tipoEdital);
        }

        return entity;
    }
}


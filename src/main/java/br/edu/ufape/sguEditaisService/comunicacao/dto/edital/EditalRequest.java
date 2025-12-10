package br.edu.ufape.sguEditaisService.comunicacao.dto.edital;

import br.edu.ufape.sguEditaisService.comunicacao.annotations.DatasConsistentes;
import br.edu.ufape.sguEditaisService.models.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @DatasConsistentes
public class EditalRequest {
    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    private LocalDateTime dataPublicacao;

    @NotNull(message = "A data de início de inscrição é obrigatória")
    private LocalDateTime inicioInscricao;

    @NotNull(message = "A data de fim de inscrição é obrigatória")
    private LocalDateTime fimIncricao;

    private Long statusAtualId;
    private Long tipoEditalId;
    private Long idUnidadeAdministrativa;
    private Long cursoId;

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


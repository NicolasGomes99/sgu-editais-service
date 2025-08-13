package br.edu.ufape.sguEditaisService.comunicacao.dto.etapa;

import br.edu.ufape.sguEditaisService.comunicacao.annotations.DatasConsistentes;
import br.edu.ufape.sguEditaisService.models.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @DatasConsistentes
public class EtapaRequest {
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotNull(message = "A data de início é obrigatória")
    private LocalDateTime dataInicio;

    @NotNull(message = "A data de fim é obrigatória")
    private LocalDateTime dataFim;


    private boolean obrigatoria;
    private int ordem;

    private Long editalId;
    private Long statusAtualId;

    public Etapa convertToEntity(EtapaRequest request, ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Etapa entity = modelMapper.map(request, Etapa.class);

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

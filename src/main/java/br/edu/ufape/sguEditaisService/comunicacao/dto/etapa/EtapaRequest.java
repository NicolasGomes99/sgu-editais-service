package br.edu.ufape.sguEditaisService.comunicacao.dto.etapa;

import br.edu.ufape.sguEditaisService.models.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class EtapaRequest {
    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;
    private LocalDateTime dataInicio;
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

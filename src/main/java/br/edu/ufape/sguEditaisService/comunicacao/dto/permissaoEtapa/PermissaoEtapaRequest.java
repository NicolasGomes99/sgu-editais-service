package br.edu.ufape.sguEditaisService.comunicacao.dto.permissaoEtapa;

import br.edu.ufape.sguEditaisService.models.Etapa;
import br.edu.ufape.sguEditaisService.models.PermissaoEtapa;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class PermissaoEtapaRequest {
    @NotBlank
    private String perfil;
    private Long etapaId;

    public PermissaoEtapa convertToEntity(PermissaoEtapaRequest request, ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        PermissaoEtapa entity = modelMapper.map(request, PermissaoEtapa.class);

        if (request.getEtapaId() != null) {
            Etapa etapa = new Etapa();
            etapa.setId(request.getEtapaId());
            entity.setEtapa(etapa);
        }

        return entity;
    }
}

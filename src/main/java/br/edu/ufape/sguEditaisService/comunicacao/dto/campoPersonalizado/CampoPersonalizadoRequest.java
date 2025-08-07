package br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado;

import br.edu.ufape.sguEditaisService.models.Edital;
import br.edu.ufape.sguEditaisService.models.Etapa;
import br.edu.ufape.sguEditaisService.models.enums.TipoCampo;
import br.edu.ufape.sguEditaisService.models.CampoPersonalizado;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class CampoPersonalizadoRequest {
    @NotBlank
    private String nome;
    private String rotulo;
    private boolean obrigatorio;
    private TipoCampo tipoCampo;
    private String opcoes;
    private Long etapaId;
    private Long editalId;

    public CampoPersonalizado convertToEntity(CampoPersonalizadoRequest request, ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CampoPersonalizado entity = modelMapper.map(request, CampoPersonalizado.class);

        if (request.getEtapaId() != null) {
            Etapa etapa = new Etapa();
            etapa.setId(request.getEtapaId());
            entity.setEtapa(etapa);
        }

        if (request.getEditalId() != null) {
            Edital edital = new Edital();
            edital.setId(request.getEditalId());
            entity.setEdital(edital);
        }

        return entity;
    }
}

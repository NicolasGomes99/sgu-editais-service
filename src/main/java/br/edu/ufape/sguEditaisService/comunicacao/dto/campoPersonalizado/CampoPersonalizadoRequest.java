package br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado;

import br.edu.ufape.sguEditaisService.comunicacao.annotations.ValidJsonOrNull;
import br.edu.ufape.sguEditaisService.models.CampoPersonalizado;
import br.edu.ufape.sguEditaisService.models.Edital;
import br.edu.ufape.sguEditaisService.models.Etapa;
import br.edu.ufape.sguEditaisService.models.TipoEdital;
import br.edu.ufape.sguEditaisService.models.enums.TipoCampo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@ValidJsonOrNull
public class CampoPersonalizadoRequest {
    @NotBlank
    private String nome;
    private String rotulo;
    private boolean obrigatorio;

    @NotNull
    private TipoCampo tipoCampo;

    private String opcoes;
    private Long etapaId;
    private Long editalId;
    private Long tipoEditalModeloId;

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

        if (request.getTipoEditalModeloId() != null) {
            TipoEdital tipoEdital = new TipoEdital();
            tipoEdital.setId(request.getTipoEditalModeloId());
            entity.setTipoEditalModelo(tipoEdital);
        }

        return entity;
    }
}
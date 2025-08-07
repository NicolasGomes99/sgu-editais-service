package br.edu.ufape.sguEditaisService.comunicacao.dto.valorCampo;

import br.edu.ufape.sguEditaisService.models.CampoPersonalizado;
import br.edu.ufape.sguEditaisService.models.Inscricao;
import br.edu.ufape.sguEditaisService.models.ValorCampo;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ValorCampoRequest {
    @NotBlank
    private String valor;

    private Long inscricaoId;
    private Long campoPersonalizadoId;

    public ValorCampo convertToEntity(ValorCampoRequest request, ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ValorCampo entity = modelMapper.map(request, ValorCampo.class);

        if (request.getInscricaoId() != null) {
            Inscricao inscricao = new Inscricao();
            inscricao.setId(request.getInscricaoId());
            entity.setInscricao(inscricao);
        }

        if (request.getCampoPersonalizadoId() != null) {
            CampoPersonalizado campo = new CampoPersonalizado();
            campo.setId(request.getCampoPersonalizadoId());
            entity.setCampoPersonalizado(campo);
        }

        return entity;
    }
}

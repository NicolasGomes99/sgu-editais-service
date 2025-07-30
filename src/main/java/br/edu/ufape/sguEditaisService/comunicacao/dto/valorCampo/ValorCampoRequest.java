package br.edu.ufape.sguEditaisService.comunicacao.dto.valorCampo;

import br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado.CampoPersonalizadoRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao.InscricaoRequest;
import br.edu.ufape.sguEditaisService.models.ValorCampo;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ValorCampoRequest {

    @NotBlank(message = "valor é obrigatório")
    private String valor;
    private Long inscricaoId;
    private Long campoPersonalizadoId;

    public ValorCampo convertToEntity(ValorCampoRequest request, ModelMapper modelMapper) {
        return modelMapper.map(request, ValorCampo.class);
    }
}

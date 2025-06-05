package br.edu.ufape.sguEditaisService.comunicacao.dto.valorCampo;

import br.edu.ufape.sguEditaisService.models.valorCampo;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ValorCampoRequest {

    @NotBlank(message = "valor é obrigatório")
    private String valor;

    public valorCampo convertToEntity(ValorCampoRequest request, ModelMapper modelMapper) {
        return modelMapper.map(request, valorCampo.class);
    }
}

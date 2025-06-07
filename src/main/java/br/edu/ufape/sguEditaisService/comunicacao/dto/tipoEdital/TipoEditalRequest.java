package br.edu.ufape.sguEditaisService.comunicacao.dto.tipoEdital;

import br.edu.ufape.sguEditaisService.models.tipoEdital;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class TipoEditalRequest {

    @NotBlank(message = "descricao é obrigatória")
    private String descricao;

    public tipoEdital convertToEntity(TipoEditalRequest request, ModelMapper modelMapper) {
        return modelMapper.map(request, tipoEdital.class);
    }
}

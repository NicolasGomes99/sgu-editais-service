package br.edu.ufape.sguEditaisService.comunicacao.dto.tipoEdital;

import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.EditalRequest;
import br.edu.ufape.sguEditaisService.models.TipoEdital;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class TipoEditalRequest {

    @NotBlank(message = "descricao é obrigatória")
    private String descricao;

    public TipoEdital convertToEntity(TipoEditalRequest request, ModelMapper modelMapper) {
        return modelMapper.map(request, TipoEdital.class);
    }
}

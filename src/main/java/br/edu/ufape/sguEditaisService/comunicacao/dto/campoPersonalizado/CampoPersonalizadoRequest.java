package br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado;

import br.edu.ufape.sguEditaisService.models.enums.tipoCampo;
import br.edu.ufape.sguEditaisService.models.campoPersonalizado;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class CampoPersonalizadoRequest {
    @NotBlank(message = "nome é obrigatório")
    private String nome;
    @NotBlank(message = "rotulo é obrigatório")
    private String rotulo;
    private boolean obrigatorio;
    private tipoCampo tipoCampo;
    @NotBlank(message = "opcoes é obrigatório")
    private String opcoes;

    public campoPersonalizado convertToEntity(CampoPersonalizadoRequest request, ModelMapper modelMapper) {
        return modelMapper.map(request, campoPersonalizado.class);
    }
}

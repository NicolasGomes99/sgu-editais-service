package br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado;

import br.edu.ufape.sguEditaisService.models.enums.TipoCampo;
import br.edu.ufape.sguEditaisService.models.CampoPersonalizado;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;

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
        return modelMapper.map(request, CampoPersonalizado.class);
    }
}

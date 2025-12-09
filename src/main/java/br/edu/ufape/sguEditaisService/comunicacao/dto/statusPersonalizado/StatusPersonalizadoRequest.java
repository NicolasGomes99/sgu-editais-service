package br.edu.ufape.sguEditaisService.comunicacao.dto.statusPersonalizado;

import br.edu.ufape.sguEditaisService.models.*;
import br.edu.ufape.sguEditaisService.models.enums.TipoStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class StatusPersonalizadoRequest {
    @NotBlank
    private String nome;

    @NotNull
    private TipoStatus tipoStatus;

    private boolean concluiEtapa;

    public StatusPersonalizado convertToEntity(StatusPersonalizadoRequest request, ModelMapper modelMapper) {
        return modelMapper.map(request, StatusPersonalizado.class);
    }
}

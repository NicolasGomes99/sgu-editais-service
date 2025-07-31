package br.edu.ufape.sguEditaisService.comunicacao.dto.statusPersonalizado;

import br.edu.ufape.sguEditaisService.models.*;
import br.edu.ufape.sguEditaisService.models.enums.TipoStatus;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class StatusPersonalizadoResponse {
    private Long id;
    private String nome;
    private TipoStatus tipoStatus;

    public StatusPersonalizadoResponse(StatusPersonalizado entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("StatusPersonalizado não pode ser nulo");
        else modelMapper.map(entity, this);
    }
}

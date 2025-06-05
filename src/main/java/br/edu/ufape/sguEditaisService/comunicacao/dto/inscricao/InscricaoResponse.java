package br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao;

import br.edu.ufape.sguEditaisService.models.enums.status;
import br.edu.ufape.sguEditaisService.models.inscricao;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class InscricaoResponse {
    private Long id;
    private status status;

    public InscricaoResponse(inscricao entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("inscricao não pode ser nulo");
        else modelMapper.map(entity, this);
    }
}

package br.edu.ufape.sguEditaisService.comunicacao.dto.tipoEdital;

import br.edu.ufape.sguEditaisService.models.tipoEdital;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class TipoEditalResponse {
    private Long id;
    private String descricao;

    public TipoEditalResponse(tipoEdital entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("tipoEdital não pode ser nulo");
        else modelMapper.map(entity, this);
    }
}

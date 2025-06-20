package br.edu.ufape.sguEditaisService.comunicacao.dto.tipoEdital;

import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.EditalResponse;
import br.edu.ufape.sguEditaisService.models.TipoEdital;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class TipoEditalResponse {
    private Long id;
    private String descricao;
    private EditalResponse edital;

    public TipoEditalResponse(TipoEdital entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("tipoEdital não pode ser nulo");
        else modelMapper.map(entity, this);
    }
}

package br.edu.ufape.sguEditaisService.comunicacao.dto.valorCampo;

import br.edu.ufape.sguEditaisService.models.valorCampo;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class ValorCampoResponse {
    private Long id;
    private String valor;

    public ValorCampoResponse(valorCampo entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("valorCampo não pode ser nulo");
        else modelMapper.map(entity, this);
    }
}

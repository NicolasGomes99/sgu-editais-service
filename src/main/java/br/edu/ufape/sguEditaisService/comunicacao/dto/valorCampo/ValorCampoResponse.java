package br.edu.ufape.sguEditaisService.comunicacao.dto.valorCampo;

import br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado.CampoPersonalizadoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao.InscricaoResponse;
import br.edu.ufape.sguEditaisService.models.ValorCampo;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class ValorCampoResponse {
    private Long id;
    private String valor;
    private InscricaoResponse inscricao;
    private CampoPersonalizadoResponse campoPersonalizado;

    public ValorCampoResponse(ValorCampo entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("valorCampo não pode ser nulo");
        else modelMapper.map(entity, this);
    }
}

package br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado;

import br.edu.ufape.sguEditaisService.models.enums.tipoCampo;
import br.edu.ufape.sguEditaisService.models.campoPersonalizado;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class CampoPersonalizadoResponse {
    private Long id;
    private String nome;
    private String rotulo;
    private boolean obrigatorio;
    private tipoCampo tipoCampo;
    private String opcoes;

    public CampoPersonalizadoResponse(campoPersonalizado entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("campoPersonalizado não pode ser nulo");
        else modelMapper.map(entity, this);
    }
}

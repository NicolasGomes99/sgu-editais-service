package br.edu.ufape.sguEditaisService.comunicacao.dto.permissaoEtapa;

import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaResponse;
import br.edu.ufape.sguEditaisService.models.PermissaoEtapa;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class PermissaoEtapaResponse {
    private Long id;
    private String perfil;
    private EtapaResponse etapa;

    public PermissaoEtapaResponse(PermissaoEtapa entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("PermissaoEtapa não pode ser nulo");
        else modelMapper.map(entity, this);
    }
}

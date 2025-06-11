package br.edu.ufape.sguEditaisService.comunicacao.dto.permissaoEtapa;

import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaRequest;
import br.edu.ufape.sguEditaisService.models.PermissaoEtapa;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class PermissaoEtapaRequest {
    @NotBlank(message = "Permissão é obrigatória")
    private String perfil;
    private EtapaRequest etapa;

    public PermissaoEtapa convertToEntity(PermissaoEtapaRequest request, ModelMapper modelMapper) {
        return modelMapper.map(request, PermissaoEtapa.class);
    }
}

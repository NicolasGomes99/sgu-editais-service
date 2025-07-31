package br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao;

import br.edu.ufape.sguEditaisService.models.Inscricao;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.modelmapper.ModelMapper;
import java.util.UUID;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class InscricaoRequest {
    @NotNull
    private UUID idUsuario;
    private Long editalId;
    private Long statusAtualId;

    public Inscricao convertToEntity(InscricaoRequest request, ModelMapper modelMapper) {
        return modelMapper.map(request, Inscricao.class);
    }
}

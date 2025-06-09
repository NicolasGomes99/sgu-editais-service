package br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao;

import br.edu.ufape.sguEditaisService.models.enums.status;
import br.edu.ufape.sguEditaisService.models.Inscricao;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class InscricaoRequest {

    private status status;

    public Inscricao convertToEntity(InscricaoRequest request, ModelMapper modelMapper) {
        return modelMapper.map(request, Inscricao.class);
    }
}

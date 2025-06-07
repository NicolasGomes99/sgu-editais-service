package br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao;

import br.edu.ufape.sguEditaisService.models.enums.status;
import br.edu.ufape.sguEditaisService.models.inscricao;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class InscricaoRequest {

    private status status;

    public inscricao convertToEntity(InscricaoRequest request, ModelMapper modelMapper) {
        return modelMapper.map(request, inscricao.class);
    }
}

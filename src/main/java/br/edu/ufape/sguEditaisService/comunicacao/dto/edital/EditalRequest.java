package br.edu.ufape.sguEditaisService.comunicacao.dto.edital;

import br.edu.ufape.sguEditaisService.models.edital;
import br.edu.ufape.sguEditaisService.models.enums.status;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class EditalRequest {

    @NotBlank(message = "titulo é obrigatório")
    private String titulo;
    @NotBlank(message = "descricao é obrigatório")
    private String descricao;
    private java.time.LocalDateTime dataPublicacao;
    private java.time.LocalDateTime inicioInscricao;
    private java.time.LocalDateTime fimIncricao;
    private status status;

    public edital convertToEntity(EditalRequest request, ModelMapper modelMapper) {
        return modelMapper.map(request, edital.class);
    }
}

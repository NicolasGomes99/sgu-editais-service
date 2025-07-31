package br.edu.ufape.sguEditaisService.comunicacao.dto.edital;

import br.edu.ufape.sguEditaisService.models.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;
import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class EditalRequest {
    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;

    private LocalDateTime dataPublicacao;
    private LocalDateTime inicioInscricao;
    private LocalDateTime fimIncricao;

    private Long statusAtualId;
    private Long tipoEditalId;

    public Edital convertToEntity(EditalRequest request, ModelMapper modelMapper) {
        return modelMapper.map(request, Edital.class);
    }
}


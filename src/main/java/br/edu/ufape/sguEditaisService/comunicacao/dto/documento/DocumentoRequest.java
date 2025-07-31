package br.edu.ufape.sguEditaisService.comunicacao.dto.documento;

import br.edu.ufape.sguEditaisService.models.Documento;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;
import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class DocumentoRequest {
    @NotBlank(message = "nome é obrigatório")
    private String nome;
    @NotBlank(message = "caminho é obrigatório")
    private String caminho;
    private LocalDateTime dataUpload;
    private Long etapaId;
    private Long inscricaoId;

    public Documento convertToEntity(DocumentoRequest request, ModelMapper modelMapper) {
        return modelMapper.map(request, Documento.class);
    }
}
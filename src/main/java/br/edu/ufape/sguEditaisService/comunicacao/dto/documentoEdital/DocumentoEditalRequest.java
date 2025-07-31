package br.edu.ufape.sguEditaisService.comunicacao.dto.documentoEdital;

import br.edu.ufape.sguEditaisService.models.DocumentoEdital;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;
import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class DocumentoEditalRequest {
    @NotBlank
    private String nome;
    @NotBlank
    private String caminho;
    private LocalDateTime dataUpload;
    private Long editalId;

    public DocumentoEdital convertToEntity(DocumentoEditalRequest request, ModelMapper modelMapper) {
        return modelMapper.map(request, DocumentoEdital.class);
    }
}

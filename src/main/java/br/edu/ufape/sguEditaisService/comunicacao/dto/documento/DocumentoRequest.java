package br.edu.ufape.sguEditaisService.comunicacao.dto.documento;

import br.edu.ufape.sguEditaisService.models.documento;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class DocumentoRequest {
    @NotBlank(message = "nome é obrigatório")
    private String nome;
    @NotBlank(message = "caminho é obrigatório")
    private String caminho;
    private java.time.LocalDateTime dataUpload;

    public documento convertToEntity(DocumentoRequest request, ModelMapper modelMapper) {
        return modelMapper.map(request, documento.class);
    }
}

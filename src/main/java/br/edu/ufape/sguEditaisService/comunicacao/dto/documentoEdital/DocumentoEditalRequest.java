package br.edu.ufape.sguEditaisService.comunicacao.dto.documentoEdital;

import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.EditalRequest;
import br.edu.ufape.sguEditaisService.models.DocumentoEdital;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class DocumentoEditalRequest {

    @NotBlank(message = "nome é obrigatório")
    private String nome;
    @NotBlank(message = "caminho é obrigatório")
    private String caminho;
    private java.time.LocalDateTime dataUpload;
    private EditalRequest edital;

    public DocumentoEdital convertToEntity(DocumentoEditalRequest request, ModelMapper modelMapper) {
        return modelMapper.map(request, DocumentoEdital.class);
    }
}

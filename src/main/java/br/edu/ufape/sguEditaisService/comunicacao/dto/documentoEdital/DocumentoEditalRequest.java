package br.edu.ufape.sguEditaisService.comunicacao.dto.documentoEdital;

import br.edu.ufape.sguEditaisService.models.DocumentoEdital;
import br.edu.ufape.sguEditaisService.models.Edital;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

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
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        DocumentoEdital entity = modelMapper.map(request, DocumentoEdital.class);

        if (request.getEditalId() != null) {
            Edital edital = new Edital();
            edital.setId(request.getEditalId());
            entity.setEdital(edital);
        }

        return entity;
    }
}

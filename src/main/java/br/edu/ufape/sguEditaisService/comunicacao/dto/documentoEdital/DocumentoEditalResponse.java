package br.edu.ufape.sguEditaisService.comunicacao.dto.documentoEdital;

import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.EditalResponse;
import br.edu.ufape.sguEditaisService.models.DocumentoEdital;
import lombok.*;
import org.modelmapper.ModelMapper;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor
public class DocumentoEditalResponse {
    private Long id;
    private String nome;
    private String caminho;
    private LocalDateTime dataUpload;

    private EditalResponse edital;

    public DocumentoEditalResponse(DocumentoEdital entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("documentoEdital não pode ser nulo");
        else modelMapper.map(entity, this);
    }
}

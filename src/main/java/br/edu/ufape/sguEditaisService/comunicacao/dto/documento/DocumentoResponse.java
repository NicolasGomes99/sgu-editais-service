package br.edu.ufape.sguEditaisService.comunicacao.dto.documento;

import br.edu.ufape.sguEditaisService.models.Documento;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class DocumentoResponse {
    private Long id;
    private String nome;
    private String caminho;
    private java.time.LocalDateTime dataUpload;

    public DocumentoResponse(Documento entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("documento não pode ser nulo");
        else modelMapper.map(entity, this);
    }
}

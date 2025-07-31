package br.edu.ufape.sguEditaisService.comunicacao.dto.documento;

import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao.InscricaoResponse;
import br.edu.ufape.sguEditaisService.models.Documento;
import lombok.*;
import org.modelmapper.ModelMapper;
import java.time.LocalDateTime;

@Getter @Setter
public class DocumentoResponse {
    private Long id;
    private String nome;
    private String caminho;
    private LocalDateTime dataUpload;
    private EtapaResponse etapa;
    private InscricaoResponse inscricao;

    public DocumentoResponse(Documento entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("documento não pode ser nulo");
        else modelMapper.map(entity, this);
    }
}
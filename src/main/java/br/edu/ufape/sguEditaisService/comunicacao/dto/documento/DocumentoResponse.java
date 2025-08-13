package br.edu.ufape.sguEditaisService.comunicacao.dto.documento;

import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao.InscricaoResponse;
import br.edu.ufape.sguEditaisService.models.Documento;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.modelmapper.ModelMapper;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter @Setter @NoArgsConstructor
public class DocumentoResponse {
    private Long id;
    private String nome;
    private String caminho;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataUpload;

    private EtapaResponse etapa;
    private InscricaoResponse inscricao;

    public DocumentoResponse(Documento entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("documento não pode ser nulo");
        else modelMapper.map(entity, this);

        this.dataUpload = entity.getDataUpload()
                .atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(ZoneId.of("America/Sao_Paulo"))
                .toLocalDateTime();
    }
}
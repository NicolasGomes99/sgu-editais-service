package br.edu.ufape.sguEditaisService.comunicacao.dto.documentoEdital;

import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.EditalResponse;
import br.edu.ufape.sguEditaisService.models.DocumentoEdital;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.modelmapper.ModelMapper;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter @Setter @NoArgsConstructor
public class DocumentoEditalResponse {
    private Long id;
    private String nome;
    private String caminho;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataUpload;

//    private EditalResponse edital;

    public DocumentoEditalResponse(DocumentoEdital entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("documentoEdital não pode ser nulo");
        else modelMapper.map(entity, this);

        this.dataUpload = entity.getDataUpload()
                .atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(ZoneId.of("America/Sao_Paulo"))
                .toLocalDateTime();
    }
}

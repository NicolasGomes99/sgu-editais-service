package br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao;

import br.edu.ufape.sguEditaisService.comunicacao.dto.documento.DocumentoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.EditalResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.statusPersonalizado.StatusPersonalizadoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.usuario.UsuarioResponse;
import br.edu.ufape.sguEditaisService.models.Inscricao;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @NoArgsConstructor
public class InscricaoResponse {
    private Long id;
    private UsuarioResponse usuario;
    private LocalDateTime dataInscricao;
    private EditalResponse edital;
    private List<DocumentoResponse> documentos;
    private StatusPersonalizadoResponse statusAtual;

    public InscricaoResponse(Inscricao entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("inscricao não pode ser nulo");
        modelMapper.map(entity, this);

        if (entity.getDocumentos() != null) {
            this.documentos = entity.getDocumentos().stream()
                    .map(doc -> new DocumentoResponse(doc, modelMapper))
                    .collect(Collectors.toList());
        }
    }

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    public LocalDateTime getDataInscricao() {
        if (this.dataInscricao == null) {
            return null;
        }
        return this.dataInscricao
                .atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(ZoneId.of("America/Sao_Paulo"))
                .toLocalDateTime();
    }
}

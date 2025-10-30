package br.edu.ufape.sguEditaisService.comunicacao.dto.historicoEtapaInscricao;

import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao.InscricaoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.statusPersonalizado.StatusPersonalizadoResponse;
import br.edu.ufape.sguEditaisService.models.HistoricoEtapaInscricao;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter @Setter @NoArgsConstructor
public class HistoricoEtapaInscricaoResponse {
    private Long id;
    private String observacao;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataAcao;

    private EtapaResponse etapa;
    private InscricaoResponse inscricao;
    private StatusPersonalizadoResponse status;

    public HistoricoEtapaInscricaoResponse(HistoricoEtapaInscricao entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("HistoricoEtapaInscricao não pode ser nulo");
        else modelMapper.map(entity, this);

        if (entity.getStatusPersonalizado() != null) {
            this.status = new StatusPersonalizadoResponse(entity.getStatusPersonalizado(), modelMapper);
        }

        this.dataAcao = entity.getDataAacao()
                .atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(ZoneId.of("America/Sao_Paulo"))
                .toLocalDateTime();
    }
}

package br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao;

import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.EditalResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.statusPersonalizado.StatusPersonalizadoResponse;
import br.edu.ufape.sguEditaisService.models.Inscricao;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor
public class InscricaoResponse {
    private Long id;
    private UUID idUsuario;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataInscricao;

    private EditalResponse edital;
    private StatusPersonalizadoResponse statusAtual;

    public InscricaoResponse(Inscricao entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("inscricao não pode ser nulo");
        else modelMapper.map(entity, this);

        this.dataInscricao = entity.getDataInscricao()
                .atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(ZoneId.of("America/Sao_Paulo"))
                .toLocalDateTime();
    }
}

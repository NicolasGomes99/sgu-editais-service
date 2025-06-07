package br.edu.ufape.sguEditaisService.comunicacao.dto.historicoEtapaInscricao;

import br.edu.ufape.sguEditaisService.models.HistoricoEtapaInscricao;
import br.edu.ufape.sguEditaisService.models.enums.status;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Getter @Setter
public class HistoricoEtapaInscricaoResponse {
    private Long id;
    private Date dataAacao;
    private status status;
    private String observacao;

    public HistoricoEtapaInscricaoResponse(HistoricoEtapaInscricao entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("HistoricoEtapaInscricao não pode ser nulo");
        else modelMapper.map(entity, this);
    }
}

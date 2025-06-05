package br.edu.ufape.sguEditaisService.comunicacao.dto.etapa;

import br.edu.ufape.sguEditaisService.models.etapa;
import br.edu.ufape.sguEditaisService.models.enums.statusEtapa;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter @Setter
public class EtapaResponse {

    private Long id;
    private String nome;
    private String descricao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private boolean obrigatoria;
    private int ordem;
    private statusEtapa status;

    public EtapaResponse(etapa entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("etapa não pode ser nulo");
        else modelMapper.map(entity, this);
    }
}

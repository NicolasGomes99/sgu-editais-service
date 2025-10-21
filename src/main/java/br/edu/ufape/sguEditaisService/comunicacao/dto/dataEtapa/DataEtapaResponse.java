package br.edu.ufape.sguEditaisService.comunicacao.dto.dataEtapa;

import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaResponse;
import br.edu.ufape.sguEditaisService.models.DataEtapa;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.Getter;
import lombok.Setter;

import org.modelmapper.ModelMapper;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DataEtapaResponse {
    private Long id;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private EtapaResponse etapa;

    public DataEtapaResponse(DataEtapa entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("DataEtapa não pode ser nulo");

        this.id = entity.getId();
        this.dataInicio = entity.getDataInicio();
        this.dataFim = entity.getDataFim();
        if (entity.getEtapa() != null) {
            this.etapa = new EtapaResponse(entity.getEtapa(), modelMapper);
        }
    }

}
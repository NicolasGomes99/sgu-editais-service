package br.edu.ufape.sguEditaisService.comunicacao.dto.dataEtapa;

import br.edu.ufape.sguEditaisService.comunicacao.annotations.DatasConsistentes;
import br.edu.ufape.sguEditaisService.models.DataEtapa;
import br.edu.ufape.sguEditaisService.models.Edital;
import br.edu.ufape.sguEditaisService.models.Etapa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @DatasConsistentes
public class DataEtapaRequest {
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private Long etapaId;
    private Long editalId;

    public DataEtapa convertToEntity(ModelMapper modelMapper) { // modelMapper ainda pode ser útil no futuro, mantemos como parâmetro
        DataEtapa entity = new DataEtapa();
        entity.setDataInicio(this.getDataInicio());
        entity.setDataFim(this.getDataFim());

        if (this.getEtapaId() != null) {
            Etapa etapa = new Etapa();
            etapa.setId(this.getEtapaId());
            entity.setEtapa(etapa);
        }

        if (this.getEditalId() != null) {
            Edital edital = new Edital();
            edital.setId(this.getEditalId());
            entity.setEdital(edital);
        }

        return entity;
    }
}
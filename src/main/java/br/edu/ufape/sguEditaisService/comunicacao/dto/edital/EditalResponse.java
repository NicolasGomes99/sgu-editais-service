package br.edu.ufape.sguEditaisService.comunicacao.dto.edital;

import br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado.CampoPersonalizadoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.dataEtapa.DataEtapaResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.statusPersonalizado.StatusPersonalizadoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.tipoEdital.TipoEditalResponse;
import br.edu.ufape.sguEditaisService.models.Edital;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import java.util.Comparator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @NoArgsConstructor
public class EditalResponse {
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataPublicacao;
    private LocalDateTime inicioInscricao;
    private LocalDateTime fimIncricao;
    private StatusPersonalizadoResponse statusAtual;
    private TipoEditalResponse tipoEdital;

    private List<DataEtapaResponse> datasEtapas;
    private List<CampoPersonalizadoResponse> camposPersonalizados;


    public EditalResponse(Edital entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("edital não pode ser nulo");

        modelMapper.map(entity, this);

        if (entity.getDatasEtapas() != null) {
            this.datasEtapas = entity.getDatasEtapas().stream()
                    .map(dataEtapa -> new DataEtapaResponse(dataEtapa, modelMapper))
                    .sorted(Comparator.comparingInt(dto -> dto.getEtapa().getOrdem()))
                    .collect(Collectors.toList());
        }

        if (entity.getCamposPersonalizados() != null) {
            this.camposPersonalizados = entity.getCamposPersonalizados().stream()
                    .map(campo -> new CampoPersonalizadoResponse(campo, modelMapper))
                    .collect(Collectors.toList());
        }
    }
}
package br.edu.ufape.sguEditaisService.comunicacao.dto.etapa;

import br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado.CampoPersonalizadoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.statusPersonalizado.StatusPersonalizadoResponse;
import br.edu.ufape.sguEditaisService.models.Etapa;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @NoArgsConstructor
public class EtapaResponse {
    private Long id;
    private String nome;
    private String descricao;
    private boolean obrigatoria;
    private int ordem;

    private StatusPersonalizadoResponse statusAtual;

    private List<CampoPersonalizadoResponse> camposPersonalizados;

    public EtapaResponse(Etapa entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("etapa não pode ser nulo");

        modelMapper.map(entity, this);

        if (entity.getCamposPersonalizados() != null) {
            this.camposPersonalizados = entity.getCamposPersonalizados().stream()
                    .map(campo -> new CampoPersonalizadoResponse(campo, modelMapper))
                    .collect(Collectors.toList());
        }
    }
}
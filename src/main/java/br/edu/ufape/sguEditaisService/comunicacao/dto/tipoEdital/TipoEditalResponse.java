package br.edu.ufape.sguEditaisService.comunicacao.dto.tipoEdital;

import br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado.CampoPersonalizadoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.unidadeAdministrativa.UnidadeAdministrativaResponse;
import br.edu.ufape.sguEditaisService.models.TipoEdital;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @NoArgsConstructor
public class TipoEditalResponse {
    private Long id;
    private String nome;
    private String descricao;

    private UnidadeAdministrativaResponse unidadeAdministrativa;

    private List<EtapaResponse> etapasModelo;
    private List<CampoPersonalizadoResponse> camposPersonalizadosModelo;


    public TipoEditalResponse(TipoEdital entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("tipoEdital não pode ser nulo");

        modelMapper.map(entity, this);

        if (entity.getEtapasModelo() != null) {
            this.etapasModelo = entity.getEtapasModelo().stream()
                    .map(etapa -> new EtapaResponse(etapa, modelMapper))
                    .collect(Collectors.toList());
        }

        if (entity.getCamposPersonalizadosModelo() != null) {
            this.camposPersonalizadosModelo = entity.getCamposPersonalizadosModelo().stream()
                    .map(campo -> new CampoPersonalizadoResponse(campo, modelMapper))
                    .collect(Collectors.toList());
        }
    }
}
package br.edu.ufape.sguEditaisService.comunicacao.dto.etapa;

import br.edu.ufape.sguEditaisService.models.Edital;
import br.edu.ufape.sguEditaisService.models.Etapa;
import br.edu.ufape.sguEditaisService.models.StatusPersonalizado;
import br.edu.ufape.sguEditaisService.models.TipoEdital;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;


@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class EtapaRequest {
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    private String descricao;
    private boolean obrigatoria;
    private int ordem;

    private Long editalId;
    private Long tipoEditalModeloId;
    private Long statusAtualId;

    public Etapa convertToEntity(EtapaRequest request, ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Etapa entity = modelMapper.map(request, Etapa.class);

        if (request.getEditalId() != null) {
            Edital edital = new Edital();
            edital.setId(request.getEditalId());
            entity.setEdital(edital);
        }

        if (request.getTipoEditalModeloId() != null) {
            TipoEdital tipoEdital = new TipoEdital();
            tipoEdital.setId(request.getTipoEditalModeloId());
            entity.setTipoEditalModelo(tipoEdital);
        }

        if (request.getStatusAtualId() != null) {
            StatusPersonalizado status = new StatusPersonalizado();
            status.setId(request.getStatusAtualId());
            entity.setStatusAtual(status);
        }

        return entity;
    }
}
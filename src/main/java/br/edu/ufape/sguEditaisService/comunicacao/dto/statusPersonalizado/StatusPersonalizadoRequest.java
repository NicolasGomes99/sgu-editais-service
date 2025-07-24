package br.edu.ufape.sguEditaisService.comunicacao.dto.statusPersonalizado;

import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.EditalRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.historicoEtapaInscricao.HistoricoEtapaInscricaoRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao.InscricaoRequest;
import br.edu.ufape.sguEditaisService.models.*;
import br.edu.ufape.sguEditaisService.models.enums.TipoStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class StatusPersonalizadoRequest {

    private String nome;
    private TipoStatus tipoStatus;

    private EditalRequest edital;
    private EtapaRequest etapa;
    private HistoricoEtapaInscricaoRequest historicoEtapaInscricao;
    private InscricaoRequest inscricao;

    public StatusPersonalizado convertToEntity(StatusPersonalizadoRequest request, ModelMapper modelMapper) {
        return modelMapper.map(request, StatusPersonalizado.class);
    }

}

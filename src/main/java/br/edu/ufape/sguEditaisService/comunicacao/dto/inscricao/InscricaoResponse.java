package br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao;

import br.edu.ufape.sguEditaisService.comunicacao.dto.documento.DocumentoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.EditalResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.historicoEtapaInscricao.HistoricoEtapaInscricaoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.valorCampo.ValorCampoResponse;
import br.edu.ufape.sguEditaisService.models.StatusPersonalizado;
import br.edu.ufape.sguEditaisService.models.enums.Status;
import br.edu.ufape.sguEditaisService.models.Inscricao;
import lombok.*;
import org.modelmapper.ModelMapper;
import java.util.List;

@Getter @Setter
public class InscricaoResponse {
    private Long id;
    private StatusPersonalizado statusPersonalizado;
    private Status status;
    private List<DocumentoResponse> documentos;
    private List<ValorCampoResponse> valoresCampos;
    private EditalResponse edital;
    private List<HistoricoEtapaInscricaoResponse> historicoEtapaInscricao;

    public InscricaoResponse(Inscricao entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("inscricao não pode ser nulo");
        else modelMapper.map(entity, this);
    }
}

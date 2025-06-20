package br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao;

import br.edu.ufape.sguEditaisService.comunicacao.dto.documento.DocumentoRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.EditalRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.historicoEtapaInscricao.HistoricoEtapaInscricaoRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.valorCampo.ValorCampoRequest;
import br.edu.ufape.sguEditaisService.models.enums.status;
import br.edu.ufape.sguEditaisService.models.Inscricao;
import lombok.*;
import org.modelmapper.ModelMapper;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class InscricaoRequest {

    private status status;
    private List<DocumentoRequest> documentos;
    private List<ValorCampoRequest> valoresCampos;
    private EditalRequest edital;
    private  List<HistoricoEtapaInscricaoRequest> historicoEtapaInscricao;

    public Inscricao convertToEntity(InscricaoRequest request, ModelMapper modelMapper) {
        return modelMapper.map(request, Inscricao.class);
    }
}

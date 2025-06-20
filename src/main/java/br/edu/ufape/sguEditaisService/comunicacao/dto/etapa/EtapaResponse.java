package br.edu.ufape.sguEditaisService.comunicacao.dto.etapa;

import br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado.CampoPersonalizadoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.documento.DocumentoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.EditalResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.historicoEtapaInscricao.HistoricoEtapaInscricaoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.permissaoEtapa.PermissaoEtapaResponse;
import br.edu.ufape.sguEditaisService.models.*;
import br.edu.ufape.sguEditaisService.models.enums.statusEtapa;
import lombok.*;
import org.modelmapper.ModelMapper;
import java.time.LocalDateTime;
import java.util.List;

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
    private List<DocumentoResponse> documentos;
    private List<CampoPersonalizadoResponse> camposPersonalizados;
    private EditalResponse edital;
    private List<PermissaoEtapaResponse> permissaoEtapa;
    private List<HistoricoEtapaInscricaoResponse> historicoEtapaInscricao;

    public EtapaResponse(Etapa entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("etapa não pode ser nulo");
        else modelMapper.map(entity, this);
    }
}

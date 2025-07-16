package br.edu.ufape.sguEditaisService.comunicacao.dto.edital;

import br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado.CampoPersonalizadoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.documentoEdital.DocumentoEditalResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao.InscricaoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.tipoEdital.TipoEditalResponse;
import br.edu.ufape.sguEditaisService.models.*;
import lombok.*;
import org.modelmapper.ModelMapper;
import java.util.List;

@Getter @Setter
public class EditalResponse {
    private Long id;
    private String titulo;
    private String descricao;
    private java.time.LocalDateTime dataPublicacao;
    private java.time.LocalDateTime inicioInscricao;
    private java.time.LocalDateTime fimIncricao;
    private StatusPersonalizado statusPersonalizado;
    private TipoEditalResponse tipoEdital;
    private List<EtapaResponse> etapas;
    private List<InscricaoResponse> inscricao;
    private List<DocumentoEditalResponse> documentosEdital;
    private List<CampoPersonalizadoResponse> camposPersonalizados;

    public EditalResponse(Edital entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("edital não pode ser nulo");
        else modelMapper.map(entity, this);
    }
}

package br.edu.ufape.sguEditaisService.comunicacao.dto.edital;

import br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado.CampoPersonalizadoRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.documentoEdital.DocumentoEditalRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao.InscricaoRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.tipoEdital.TipoEditalRequest;
import br.edu.ufape.sguEditaisService.models.*;
import br.edu.ufape.sguEditaisService.models.enums.Status;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class EditalRequest {

    @NotBlank(message = "titulo é obrigatório")
    private String titulo;
    @NotBlank(message = "descricao é obrigatório")
    private String descricao;
    private java.time.LocalDateTime dataPublicacao;
    private java.time.LocalDateTime inicioInscricao;
    private java.time.LocalDateTime fimIncricao;
    private StatusPersonalizado statusPersonalizado;
    private Status status;
    private TipoEditalRequest tipoEdital;
    private List<EtapaRequest> etapas;
    private List<InscricaoRequest> inscricao;
    private List<DocumentoEditalRequest> documentosEdital;
    private List<CampoPersonalizadoRequest> camposPersonalizados;

    public Edital convertToEntity(EditalRequest request, ModelMapper modelMapper) {
        return modelMapper.map(request, Edital.class);
    }
}

package br.edu.ufape.sguEditaisService.comunicacao.dto.edital;

import br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado.CampoPersonalizadoRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.documentoEdital.DocumentoEditalRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao.InscricaoRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.tipoEdital.TipoEditalRequest;
import br.edu.ufape.sguEditaisService.models.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class EditalRequest {

    @NotBlank(message = "titulo é obrigatório")
    private String titulo;
    @NotBlank(message = "descricao é obrigatório")
    private String descricao;
    private LocalDateTime dataPublicacao;
    private LocalDateTime inicioInscricao;
    private LocalDateTime fimIncricao;
    private Long statusPersonalizadoId;
    private Long tipoEditalId;

    public Edital convertToEntity(EditalRequest request, ModelMapper modelMapper) {
        return modelMapper.map(request, Edital.class);
    }
}

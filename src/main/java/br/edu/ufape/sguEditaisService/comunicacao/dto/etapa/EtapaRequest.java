package br.edu.ufape.sguEditaisService.comunicacao.dto.etapa;

import br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado.CampoPersonalizadoRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.documento.DocumentoRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.EditalRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.historicoEtapaInscricao.HistoricoEtapaInscricaoRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.permissaoEtapa.PermissaoEtapaRequest;
import br.edu.ufape.sguEditaisService.models.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class EtapaRequest {
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private boolean obrigatoria;
    private int ordem;
    private Long statusPersonalizadoId;
    private Long editalId;

    public Etapa convertToEntity(EtapaRequest request, ModelMapper modelMapper) {
        return modelMapper.map(request, Etapa.class);
    }
}

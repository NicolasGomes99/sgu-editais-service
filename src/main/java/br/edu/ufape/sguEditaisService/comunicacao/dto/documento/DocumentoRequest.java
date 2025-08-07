package br.edu.ufape.sguEditaisService.comunicacao.dto.documento;

import br.edu.ufape.sguEditaisService.models.Documento;
import br.edu.ufape.sguEditaisService.models.Etapa;
import br.edu.ufape.sguEditaisService.models.Inscricao;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class DocumentoRequest {
    @NotBlank(message = "nome é obrigatório")
    private String nome;
    @NotBlank(message = "caminho é obrigatório")
    private String caminho;
    private LocalDateTime dataUpload;
    private Long etapaId;
    private Long inscricaoId;

    public Documento convertToEntity(DocumentoRequest request, ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Documento entity = modelMapper.map(request, Documento.class);

        if (request.getEtapaId() != null) {
            Etapa etapa = new Etapa();
            etapa.setId(request.getEtapaId());
            entity.setEtapa(etapa);
        }

        if (request.getInscricaoId() != null) {
            Inscricao inscricao = new Inscricao();
            inscricao.setId(request.getInscricaoId());
            entity.setInscricao(inscricao);
        }

        return entity;
    }
}
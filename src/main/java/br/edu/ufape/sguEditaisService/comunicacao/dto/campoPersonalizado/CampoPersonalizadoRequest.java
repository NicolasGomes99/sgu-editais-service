package br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado;

import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.EditalRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.valorCampo.ValorCampoRequest;
import br.edu.ufape.sguEditaisService.models.enums.TipoCampo;
import br.edu.ufape.sguEditaisService.models.CampoPersonalizado;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.Map;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class CampoPersonalizadoRequest {
    @NotBlank(message = "nome é obrigatório")
    private String nome;
    @NotBlank(message = "rotulo é obrigatório")
    private String rotulo;
    private boolean obrigatorio;
    private TipoCampo tipoCampo;
    @NotNull(message = "opcoes é obrigatório")
    private Map<String, Object> opcoes;
    private List<ValorCampoRequest> valoresCampo;
    private EtapaRequest etapa;
    private EditalRequest edital;

    public CampoPersonalizado convertToEntity(CampoPersonalizadoRequest request, ModelMapper modelMapper) {
        CampoPersonalizado entity = modelMapper.map(request, CampoPersonalizado.class);

        // Conversão segura de Map para JSON string
        try {
            ObjectMapper mapper = new ObjectMapper();
            entity.setOpcoes(mapper.writeValueAsString(request.getOpcoes()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao converter 'opcoes' para JSON válido", e);
        }

        return entity;
    }
}

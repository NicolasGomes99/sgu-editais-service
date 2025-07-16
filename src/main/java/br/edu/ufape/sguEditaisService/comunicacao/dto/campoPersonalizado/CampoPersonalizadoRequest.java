package br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado;

import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.EditalRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.valorCampo.ValorCampoRequest;
import br.edu.ufape.sguEditaisService.models.enums.TipoCampo;
import br.edu.ufape.sguEditaisService.models.CampoPersonalizado;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class CampoPersonalizadoRequest {
    @NotBlank(message = "nome é obrigatório")
    private String nome;
    @NotBlank(message = "rotulo é obrigatório")
    private String rotulo;
    private boolean obrigatorio;
    private TipoCampo tipoCampo;
    @NotBlank(message = "opcoes é obrigatório")
    private String opcoes;
    private List<ValorCampoRequest> valoresCampo;
    private EtapaRequest etapa;
    private EditalRequest edital;

    public CampoPersonalizado convertToEntity(CampoPersonalizadoRequest request, ModelMapper modelMapper) {
        return modelMapper.map(request, CampoPersonalizado.class);
    }
}

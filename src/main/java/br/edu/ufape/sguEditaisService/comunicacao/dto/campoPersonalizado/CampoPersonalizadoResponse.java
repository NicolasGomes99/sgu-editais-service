package br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado;

import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.EditalResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.valorCampo.ValorCampoResponse;
import br.edu.ufape.sguEditaisService.models.enums.TipoCampo;
import br.edu.ufape.sguEditaisService.models.CampoPersonalizado;
import lombok.*;
import org.modelmapper.ModelMapper;
import java.util.List;

@Getter @Setter
public class CampoPersonalizadoResponse {
    private Long id;
    private String nome;
    private String rotulo;
    private boolean obrigatorio;
    private TipoCampo tipoCampo;
    private String opcoes;
    private List<ValorCampoResponse> valorCampo;
    private EtapaResponse etapa;
    private EditalResponse edital;

    public CampoPersonalizadoResponse(CampoPersonalizado entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("campoPersonalizado não pode ser nulo");
        else modelMapper.map(entity, this);
    }
}

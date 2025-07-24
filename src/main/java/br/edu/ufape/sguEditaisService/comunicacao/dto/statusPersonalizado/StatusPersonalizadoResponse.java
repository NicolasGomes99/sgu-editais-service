package br.edu.ufape.sguEditaisService.comunicacao.dto.statusPersonalizado;

import br.edu.ufape.sguEditaisService.models.*;
import br.edu.ufape.sguEditaisService.models.enums.TipoStatus;
import br.edu.ufape.sguEditaisService.models.enums.tipoCampo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class StatusPersonalizadoResponse {
    private  Long id;
    private String nome;
    private TipoStatus tipoStatus;

    private Edital edital;
    private Etapa etapa;
    private HistoricoEtapaInscricao historicoEtapaInscricao;
    private Inscricao inscricao;

    public StatusPersonalizadoResponse(StatusPersonalizado entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("StatusPersonalizado não pode ser nulo");
        else modelMapper.map(entity, this);
    }
}

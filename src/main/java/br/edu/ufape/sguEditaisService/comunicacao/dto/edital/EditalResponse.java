package br.edu.ufape.sguEditaisService.comunicacao.dto.edital;

import br.edu.ufape.sguEditaisService.comunicacao.dto.statusPersonalizado.StatusPersonalizadoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.tipoEdital.TipoEditalResponse;
import br.edu.ufape.sguEditaisService.models.*;
import lombok.*;
import org.modelmapper.ModelMapper;
import java.time.LocalDateTime;

@Getter @Setter
public class EditalResponse {
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataPublicacao;
    private LocalDateTime inicioInscricao;
    private LocalDateTime fimIncricao;
    private StatusPersonalizadoResponse statusAtual;
    private TipoEditalResponse tipoEdital;

    public EditalResponse(Edital entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("edital não pode ser nulo");
        else modelMapper.map(entity, this);
    }
}

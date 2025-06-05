package br.edu.ufape.sguEditaisService.comunicacao.dto.edital;

import br.edu.ufape.sguEditaisService.models.edital;
import br.edu.ufape.sguEditaisService.models.enums.status;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class EditalResponse {
    private Long id;
    private String titulo;
    private String descricao;
    private java.time.LocalDateTime dataPublicacao;
    private java.time.LocalDateTime inicioInscricao;
    private java.time.LocalDateTime fimIncricao;
    private status status;

    public EditalResponse(edital entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("edital não pode ser nulo");
        else modelMapper.map(entity, this);
    }
}

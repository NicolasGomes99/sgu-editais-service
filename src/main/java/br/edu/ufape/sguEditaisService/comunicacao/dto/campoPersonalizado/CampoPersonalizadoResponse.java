package br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado;

import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.EditalResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaResponse;
import br.edu.ufape.sguEditaisService.models.enums.TipoCampo;
import br.edu.ufape.sguEditaisService.models.CampoPersonalizado;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.util.List;

@Getter @Setter @NoArgsConstructor
public class CampoPersonalizadoResponse {
    private Long id;
    private String nome;
    private String rotulo;
    private boolean obrigatorio;
    private TipoCampo tipoCampo;
    private String opcoes;
//    private EtapaResponse etapa;
//    private EditalResponse edital;

    public CampoPersonalizadoResponse(CampoPersonalizado entity, ModelMapper modelMapper) {
        if (entity == null) throw new IllegalArgumentException("campoPersonalizado não pode ser nulo");
        else modelMapper.map(entity, this);
    }

    public String getOpcoes() {
        // Se o campo for nulo, vazio, ou não parecer um array JSON, retorna o valor original
        if (this.opcoes == null || this.opcoes.trim().isEmpty() || !this.opcoes.trim().startsWith("[")) {
            return this.opcoes;
        }

        try {
            // Converte a string de array JSON para uma Lista de Strings
            List<String> listaOpcoes = new ObjectMapper().readValue(this.opcoes, new TypeReference<>() {
            });

            // Junta os elementos da lista em uma única string, separados por ", "
            return String.join(", ", listaOpcoes);
        } catch (IOException e) {
            // Se ocorrer um erro ao analisar o JSON, retorna a string original para não quebrar a API
            return this.opcoes;
        }
    }
}

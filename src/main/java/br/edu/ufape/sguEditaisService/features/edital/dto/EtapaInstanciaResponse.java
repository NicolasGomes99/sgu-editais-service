package br.edu.ufape.sguEditaisService.features.edital.dto;

import br.edu.ufape.sguEditaisService.features.edital.etapainstancia.EtapaInstancia;

import java.time.LocalDateTime;
import java.util.List;

public record EtapaInstanciaResponse(
        Long id,
        String nome,
        String descricao,
        Integer ordem,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        String configuracoes,
        List<CampoEtapaInstanciaResponse> campos
) {
    public static EtapaInstanciaResponse from(EtapaInstancia etapa) {
        return new EtapaInstanciaResponse(
                etapa.getId(),
                etapa.getNome(),
                etapa.getDescricao(),
                etapa.getOrdem(),
                etapa.getDataInicio(),
                etapa.getDataFim(),
                etapa.getConfiguracoes(),
                etapa.getCampos().stream().map(CampoEtapaInstanciaResponse::from).toList()
        );
    }
}
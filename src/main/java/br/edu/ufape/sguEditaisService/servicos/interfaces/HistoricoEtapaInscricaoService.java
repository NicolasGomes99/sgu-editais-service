package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.models.HistoricoEtapaInscricao;
import java.util.List;

public interface HistoricoEtapaInscricaoService {
    HistoricoEtapaInscricao salvar(HistoricoEtapaInscricao historico);
    List<HistoricoEtapaInscricao> listarPorInscricao(Long inscricaoId);
}
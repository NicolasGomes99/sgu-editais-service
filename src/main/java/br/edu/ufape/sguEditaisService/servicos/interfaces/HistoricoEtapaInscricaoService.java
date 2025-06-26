package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.models.HistoricoEtapaInscricao;
import br.edu.ufape.sguEditaisService.exceptions.notFound.HistoricoEtapaInscricaoNotFoundException;

import java.util.List;

public interface HistoricoEtapaInscricaoService {
    HistoricoEtapaInscricao salvarHistoricoEtapaInscricao(HistoricoEtapaInscricao entity);

    HistoricoEtapaInscricao buscarPorIdHistoricoEtapaInscricao(Long id) throws HistoricoEtapaInscricaoNotFoundException;

    List<HistoricoEtapaInscricao> listarHistoricoEtapaInscricao();

    HistoricoEtapaInscricao editarHistoricoEtapaInscricao(Long id, HistoricoEtapaInscricao entity) throws HistoricoEtapaInscricaoNotFoundException;

    void deletarHistoricoEtapaInscricao(Long id) throws HistoricoEtapaInscricaoNotFoundException;
}
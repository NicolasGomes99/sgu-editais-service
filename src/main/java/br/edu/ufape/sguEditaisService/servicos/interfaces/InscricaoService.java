package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.models.Inscricao;
import br.edu.ufape.sguEditaisService.exceptions.notFound.InscricaoNotFoundException;

import java.util.List;

public interface InscricaoService {
    Inscricao salvarInscricao(Inscricao entity);

    Inscricao buscarPorIdInscricao(Long id) throws InscricaoNotFoundException;

    List<Inscricao> listarInscricao();

    Inscricao editarInscricao(Long id, Inscricao entity) throws InscricaoNotFoundException;

    void deletarInscricao(Long id) throws InscricaoNotFoundException;
}

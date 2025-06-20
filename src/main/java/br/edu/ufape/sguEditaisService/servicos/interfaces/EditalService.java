package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.exceptions.notFound.EditalNotFoundException;
import br.edu.ufape.sguEditaisService.models.Edital;

import java.util.List;

public interface EditalService {
    Edital salvarEdital(Edital entity);

    Edital buscarPorIdEdital(Long id) throws EditalNotFoundException;

    List<Edital> listarEdital();

    Edital editarEdital(Long id, Edital entity) throws EditalNotFoundException;

    void deletarEdital(Long id) throws EditalNotFoundException;
}

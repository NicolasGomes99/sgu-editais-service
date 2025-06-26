package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.models.TipoEdital;
import br.edu.ufape.sguEditaisService.exceptions.notFound.TipoEditalNotFoundException;

import java.util.List;

public interface TipoEditalService {
    TipoEdital salvarTipoEdital(TipoEdital entity);

    TipoEdital buscarPorIdTipoEdital(Long id) throws TipoEditalNotFoundException;

    List<TipoEdital> listarTipoEdital();

    TipoEdital editarTipoEdital(Long id, TipoEdital entity) throws TipoEditalNotFoundException;

    void deletarTipoEdital(Long id) throws TipoEditalNotFoundException;
}

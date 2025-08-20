package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.exceptions.notFound.TipoEditalNotFoundException;
import br.edu.ufape.sguEditaisService.models.TipoEdital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TipoEditalService {
    TipoEdital salvarTipoEdital(TipoEdital entity);

    TipoEdital buscarPorIdTipoEdital(Long id) throws TipoEditalNotFoundException;

    Page<TipoEdital> listarTipoEdital(Pageable pageable);

    TipoEdital editarTipoEdital(Long id, TipoEdital entity) throws TipoEditalNotFoundException;

    void deletarTipoEdital(Long id) throws TipoEditalNotFoundException;

    TipoEdital duplicarTipoEdital(Long id);
}
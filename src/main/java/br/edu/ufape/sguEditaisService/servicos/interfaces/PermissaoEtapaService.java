package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.models.PermissaoEtapa;
import br.edu.ufape.sguEditaisService.exceptions.notFound.PermissaoEtapaNotFoundException;

import java.util.List;

public interface PermissaoEtapaService {
    PermissaoEtapa salvarPermissaoEtapa(PermissaoEtapa entity);

    PermissaoEtapa buscarPorIdPermissaoEtapa(Long id) throws PermissaoEtapaNotFoundException;

    List<PermissaoEtapa> listarPermissaoEtapa();

    PermissaoEtapa editarPermissaoEtapa(Long id, PermissaoEtapa entity) throws PermissaoEtapaNotFoundException;

    void deletarPermissaoEtapa(Long id) throws PermissaoEtapaNotFoundException;
}

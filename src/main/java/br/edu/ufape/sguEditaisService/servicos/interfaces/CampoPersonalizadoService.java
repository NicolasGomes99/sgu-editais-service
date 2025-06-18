package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.exceptions.notFound.CampoPersonalizadoNotFoundException;
import br.edu.ufape.sguEditaisService.models.CampoPersonalizado;

import java.util.List;

public interface CampoPersonalizadoService {
    CampoPersonalizado salvarCampoPersonalizado(CampoPersonalizado entity);

    CampoPersonalizado buscarPorIdCampoPersonalizado(Long id) throws CampoPersonalizadoNotFoundException;

    List<CampoPersonalizado> listarCampoPersonalizado();

    CampoPersonalizado editarCampoPersonalizado(Long id, CampoPersonalizado entity) throws CampoPersonalizadoNotFoundException;

    void deletarCampoPersonalizado(Long id) throws CampoPersonalizadoNotFoundException;
}

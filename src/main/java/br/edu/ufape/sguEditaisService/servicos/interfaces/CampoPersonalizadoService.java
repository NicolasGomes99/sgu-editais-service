package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.exceptions.CampoPersonalizadoNotFoundException;
import br.edu.ufape.sguEditaisService.models.CampoPersonalizado;

import java.util.List;

public interface CampoPersonalizadoService {
    CampoPersonalizado salvar(CampoPersonalizado entity);

    CampoPersonalizado buscar(Long id) throws CampoPersonalizadoNotFoundException;

    List<CampoPersonalizado> listar();

    CampoPersonalizado editar(Long id, CampoPersonalizado entity) throws CampoPersonalizadoNotFoundException;

    void deletar(Long id) throws CampoPersonalizadoNotFoundException;
}

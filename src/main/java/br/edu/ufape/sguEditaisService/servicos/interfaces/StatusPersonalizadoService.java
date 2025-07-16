package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.exceptions.notFound.StatusPersonalizadoNotFoundException;
import br.edu.ufape.sguEditaisService.models.StatusPersonalizado;

import java.util.List;

public interface StatusPersonalizadoService {

    StatusPersonalizado salvarStatusPersonalizado(StatusPersonalizado statusPersonalizado);

    StatusPersonalizado buscarPorIdStatusPersonalizado(Long id) throws StatusPersonalizadoNotFoundException;

    List<StatusPersonalizado> listarStatusPersonalizados();

    StatusPersonalizado editarStatusPersonalizado(Long id, StatusPersonalizado statusPersonalizado) throws StatusPersonalizadoNotFoundException;

    void deletarStatusPersonalizado(Long id) throws StatusPersonalizadoNotFoundException;
}
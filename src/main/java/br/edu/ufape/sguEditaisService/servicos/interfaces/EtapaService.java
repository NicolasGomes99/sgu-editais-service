package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.exceptions.notFound.EtapaNotFoundException;
import br.edu.ufape.sguEditaisService.models.Etapa;

import java.util.List;

public interface EtapaService {
    Etapa salvarEtapa(Etapa entity);

     Etapa buscarPorIdEtapa(Long id) throws EtapaNotFoundException;

     List<Etapa> listarEtapa();

     Etapa editarEtapa(Long id, Etapa entity) throws EtapaNotFoundException;

     void deletarEtapa(Long id) throws EtapaNotFoundException;
}

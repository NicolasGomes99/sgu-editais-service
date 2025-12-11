package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.exceptions.notFound.EtapaNotFoundException;
import br.edu.ufape.sguEditaisService.models.Etapa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EtapaService {
    Etapa salvarEtapa(Etapa entity);

    Etapa buscarPorIdEtapa(Long id) throws EtapaNotFoundException;

    Page<Etapa> listarEtapa(Pageable pageable);

    Etapa editarEtapa(Long id, Etapa entity) throws EtapaNotFoundException;

    void deletarEtapa(Long id) throws EtapaNotFoundException;

    List<Etapa> listarEtapasPorEdital(Long editalId);

    List<Etapa> listarEtapasPorTipoEdital(Long tipoEditalId);

    void atualizarOrdemEtapas(List<Long> idsEtapasEmOrdem);

    Optional<Etapa> buscarProximaEtapa(Long editalId, int ordemAtual);
}
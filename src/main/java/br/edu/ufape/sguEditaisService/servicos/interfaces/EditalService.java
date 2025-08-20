package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.exceptions.notFound.EditalNotFoundException;
import br.edu.ufape.sguEditaisService.models.Edital;
import br.edu.ufape.sguEditaisService.models.TipoEdital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EditalService {
    Edital salvarEdital(Edital entity);

    Edital buscarPorIdEdital(Long id) throws EditalNotFoundException;

    Page<Edital> listarEdital(Pageable pageable);

    Edital editarEdital(Long id, Edital entity) throws EditalNotFoundException;

    void deletarEdital(Long id) throws EditalNotFoundException;

    Edital criarEditalAPartirDeModelo(Long templateId, Edital editalBase);

    TipoEdital transformarEditalEmModelo(Long editalId, String nomeModelo, String descricaoModelo);

    Page<Edital> listarEditaisPublicados(Pageable pageable);

    Edital atualizarStatusEdital(Long editalId, Long novoStatusId);
}
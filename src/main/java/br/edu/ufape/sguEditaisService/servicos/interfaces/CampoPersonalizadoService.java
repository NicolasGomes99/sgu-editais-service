package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.exceptions.notFound.CampoPersonalizadoNotFoundException;
import br.edu.ufape.sguEditaisService.models.CampoPersonalizado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CampoPersonalizadoService {
    CampoPersonalizado salvarCampoPersonalizado(CampoPersonalizado entity);

    CampoPersonalizado buscarPorIdCampoPersonalizado(Long id) throws CampoPersonalizadoNotFoundException;

    Page<CampoPersonalizado> listarCampoPersonalizado(Pageable pageable);

    CampoPersonalizado editarCampoPersonalizado(Long id, CampoPersonalizado entity) throws CampoPersonalizadoNotFoundException;

    void deletarCampoPersonalizado(Long id) throws CampoPersonalizadoNotFoundException;

    List<CampoPersonalizado> listarCamposPorEdital(Long editalId);

    List<CampoPersonalizado> listarCamposPorTipoEdital(Long tipoEditalModeloId);

    List<CampoPersonalizado> listarCamposPorEtapa(Long etapaId);

    CampoPersonalizado alternarObrigatoriedade(Long campoPersonalizadoId);
}

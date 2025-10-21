package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.models.DataEtapa;
import java.util.List;

public interface DataEtapaService {
    DataEtapa salvarDataEtapa(DataEtapa dataEtapa);
    DataEtapa buscarDataEtapaPorId(Long id);
    List<DataEtapa> listarDatasEtapas();
    List<DataEtapa> listarDatasEtapasPorEditalId(Long editalId);
    DataEtapa editarDataEtapa(Long id, DataEtapa dataEtapa);
    void deletarDataEtapa(Long id);
}
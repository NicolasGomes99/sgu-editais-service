package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.DataEtapaRepository;
import br.edu.ufape.sguEditaisService.exceptions.notFound.NotFoundException;
import br.edu.ufape.sguEditaisService.models.DataEtapa;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataEtapaService implements br.edu.ufape.sguEditaisService.servicos.interfaces.DataEtapaService {

    private final DataEtapaRepository dataEtapaRepository;
    private final ModelMapper modelMapper;

    @Override
    public DataEtapa salvarDataEtapa(DataEtapa dataEtapa) {
        return dataEtapaRepository.save(dataEtapa);
    }

    @Override
    public DataEtapa buscarDataEtapaPorId(Long id) {
        return dataEtapaRepository.findById(id).orElseThrow(() -> new NotFoundException("DataEtapa", id) {});
    }

    @Override
    public List<DataEtapa> listarDatasEtapas() {
        return dataEtapaRepository.findAll();
    }

    @Override
    public List<DataEtapa> listarDatasEtapasPorEditalId(Long editalId) {
        return dataEtapaRepository.findAllByEditalId(editalId);
    }

    @Override
    public DataEtapa editarDataEtapa(Long id, DataEtapa dataEtapa) {
        DataEtapa dataEtapaExistente = buscarDataEtapaPorId(id);
        modelMapper.map(dataEtapa, dataEtapaExistente);
        return dataEtapaRepository.save(dataEtapaExistente);
    }

    @Override
    public void deletarDataEtapa(Long id) {
        dataEtapaRepository.deleteById(id);
    }
}
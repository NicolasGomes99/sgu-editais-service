package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.EtapaRepository;
import br.edu.ufape.sguEditaisService.exceptions.notFound.EtapaNotFoundException;
import br.edu.ufape.sguEditaisService.models.Etapa;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EtapaService implements br.edu.ufape.sguEditaisService.servicos.interfaces.EtapaService {
    private final EtapaRepository etapaRepository;
    private  final ModelMapper modelMapper;

    @Override
    public Etapa salvarEtapa(Etapa entity) {
        return etapaRepository.save(entity);
    }

    @Override
    public Etapa buscarPorIdEtapa(Long id) throws EtapaNotFoundException {
        return etapaRepository.findById(id).orElseThrow(EtapaNotFoundException::new);
    }

    @Override
    public List<Etapa> listarEtapa() {
        return etapaRepository.findAll();
    }

    @Override
    public Etapa editarEtapa(Long id, Etapa entity) throws EtapaNotFoundException {
        Etapa etapa = etapaRepository.findById(id).orElseThrow(EtapaNotFoundException::new);
        modelMapper.map(entity, etapa);
        return etapaRepository.save(etapa);
    }

    @Override
    public void deletarEtapa(Long id) throws EtapaNotFoundException{
        Etapa etapa = etapaRepository.findById(id).orElseThrow(EtapaNotFoundException::new);
        etapaRepository.delete(etapa);
    }
}

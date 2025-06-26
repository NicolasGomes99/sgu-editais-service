package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.HistoricoEtapaInscricaoRepository;
import br.edu.ufape.sguEditaisService.exceptions.notFound.HistoricoEtapaInscricaoNotFoundException;
import br.edu.ufape.sguEditaisService.models.HistoricoEtapaInscricao;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HistoricoEtapaInscricaoService implements br.edu.ufape.sguEditaisService.servicos.interfaces.HistoricoEtapaInscricaoService {
    private final HistoricoEtapaInscricaoRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public HistoricoEtapaInscricao salvarHistoricoEtapaInscricao(HistoricoEtapaInscricao entity) {
        return repository.save(entity);
    }

    @Override
    public HistoricoEtapaInscricao buscarPorIdHistoricoEtapaInscricao(Long id) throws HistoricoEtapaInscricaoNotFoundException {
        return repository.findById(id).orElseThrow(HistoricoEtapaInscricaoNotFoundException::new);
    }

    @Override
    public List<HistoricoEtapaInscricao> listarHistoricoEtapaInscricao() {
        return repository.findAll();
    }

    @Override
    public HistoricoEtapaInscricao editarHistoricoEtapaInscricao(Long id, HistoricoEtapaInscricao entity) throws HistoricoEtapaInscricaoNotFoundException {
        HistoricoEtapaInscricao original = buscarPorIdHistoricoEtapaInscricao(id);
        modelMapper.map(entity, original);
        return repository.save(original);
    }

    @Override
    public void deletarHistoricoEtapaInscricao(Long id) throws HistoricoEtapaInscricaoNotFoundException {
        HistoricoEtapaInscricao entity = buscarPorIdHistoricoEtapaInscricao(id);
        repository.delete(entity);
    }
}
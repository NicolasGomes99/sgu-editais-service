package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.InscricaoRepository;
import br.edu.ufape.sguEditaisService.exceptions.notFound.InscricaoNotFoundException;
import br.edu.ufape.sguEditaisService.models.Inscricao;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InscricaoService implements br.edu.ufape.sguEditaisService.servicos.interfaces.InscricaoService {
    private final InscricaoRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public Inscricao salvarInscricao(Inscricao entity) {
        return repository.save(entity);
    }

    @Override
    public Inscricao buscarPorIdInscricao(Long id) throws InscricaoNotFoundException {
        return repository.findById(id).orElseThrow(InscricaoNotFoundException::new);
    }

    @Override
    public List<Inscricao> listarInscricao() {
        return repository.findAll();
    }

    @Override
    public Inscricao editarInscricao(Long id, Inscricao entity) throws InscricaoNotFoundException {
        Inscricao original = buscarPorIdInscricao(id);
        modelMapper.map(entity, original);
        return repository.save(original);
    }

    @Override
    public void deletarInscricao(Long id) throws InscricaoNotFoundException {
        Inscricao entity = buscarPorIdInscricao(id);
        repository.delete(entity);
    }
}

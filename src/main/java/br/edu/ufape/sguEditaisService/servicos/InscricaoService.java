package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.InscricaoRepository;
import br.edu.ufape.sguEditaisService.exceptions.notFound.InscricaoNotFoundException;
import br.edu.ufape.sguEditaisService.models.Inscricao;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class InscricaoService implements br.edu.ufape.sguEditaisService.servicos.interfaces.InscricaoService {
    private final InscricaoRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public Inscricao salvarInscricao(Inscricao entity) {
        entity.setDataInscricao(LocalDateTime.now());
        return repository.save(entity);
    }

    @Override
    public Inscricao buscarPorIdInscricao(Long id) throws InscricaoNotFoundException {
        return repository.findById(id).orElseThrow(() -> new InscricaoNotFoundException(id));
    }

    @Override
    public Page<Inscricao> listarInscricao(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Inscricao editarInscricao(Long id, Inscricao entity) throws InscricaoNotFoundException {
        Inscricao original = repository.findById(id).orElseThrow(() -> new InscricaoNotFoundException(id));
        modelMapper.map(entity, original);
        return repository.save(original);
    }

    @Override
    public void deletarInscricao(Long id) throws InscricaoNotFoundException {
        Inscricao entity = repository.findById(id).orElseThrow(() -> new InscricaoNotFoundException(id));
        repository.delete(entity);
    }

    @Override
    public boolean existeInscricao(UUID userId, Long editalId) {
        return repository.existsByIdUsuarioAndEditalId(userId, editalId);
    }
}

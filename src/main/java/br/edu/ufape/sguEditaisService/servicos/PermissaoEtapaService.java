package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.PermissaoEtapaRepository;
import br.edu.ufape.sguEditaisService.exceptions.notFound.PermissaoEtapaNotFoundException;
import br.edu.ufape.sguEditaisService.models.PermissaoEtapa;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PermissaoEtapaService implements br.edu.ufape.sguEditaisService.servicos.interfaces.PermissaoEtapaService {
    private final PermissaoEtapaRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public PermissaoEtapa salvarPermissaoEtapa(PermissaoEtapa entity) {
        return repository.save(entity);
    }

    @Override
    public PermissaoEtapa buscarPorIdPermissaoEtapa(Long id) throws PermissaoEtapaNotFoundException {
        return repository.findById(id).orElseThrow(() -> new PermissaoEtapaNotFoundException(id));
    }

    @Override
    public List<PermissaoEtapa> listarPermissaoEtapa() {
        return repository.findAll();
    }

    @Override
    public PermissaoEtapa editarPermissaoEtapa(Long id, PermissaoEtapa entity) throws PermissaoEtapaNotFoundException {
        PermissaoEtapa original = repository.findById(id).orElseThrow(() -> new PermissaoEtapaNotFoundException(id));
        modelMapper.map(entity, original);
        return repository.save(original);
    }

    @Override
    public void deletarPermissaoEtapa(Long id) throws PermissaoEtapaNotFoundException {
        PermissaoEtapa entity = repository.findById(id).orElseThrow(() -> new PermissaoEtapaNotFoundException(id));
        repository.delete(entity);
    }

    @Override
    public List<PermissaoEtapa> listarPermissoesPorEtapa(Long etapaId) {
        return repository.findByEtapaId(etapaId);
    }

    @Override
    public Optional<PermissaoEtapa> buscarPorEtapaEPerfil(Long etapaId, String perfil) {
        return repository.findByEtapaIdAndPerfil(etapaId, perfil);
    }
}